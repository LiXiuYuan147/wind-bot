package woaini.fenger.bot.core.dispatcher.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.bind.domain.BotBind;
import woaini.fenger.bot.core.bind.service.BotUserService;
import woaini.fenger.bot.core.boot.ApplicationStartupCompleted;
import woaini.fenger.bot.core.bot.config.BotAutoConfig;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.CmdParams;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.command.dto.CmdDTO;
import woaini.fenger.bot.core.command.utils.CmdTool;
import woaini.fenger.bot.core.dispatcher.IBotInterceptor;
import woaini.fenger.bot.core.event.message.MessageEvent;
import woaini.fenger.bot.core.event.message.impl.GroupMessageEvent;
import woaini.fenger.bot.core.event.segment.Messages;
import woaini.fenger.bot.core.event.segment.Segment;
import woaini.fenger.bot.core.event.segment.impl.Mention;
import woaini.fenger.bot.core.exception.BotException;
import woaini.fenger.bot.core.session.Session;

/**
 * 调度程序命令 命令只获取纯文本信息 也就是私聊和群聊的消息 之类的
 *
 * @see CmdInterceptor
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
@Slf4j
@AllArgsConstructor
public class CmdInterceptor implements IBotInterceptor, ApplicationStartupCompleted {

  private final BotAutoConfig botAutoConfig;
  private final BotUserService botUserService;

  /**
   * @see Integer 执行顺序
   */
  public static final Integer ORDER = 100;

  public static Map<String, ICmd> cmdMaps;

  @Override
  public boolean preDispatch(Session session) {
    return session.getEvent() instanceof MessageEvent;
  }

  @Override
  public void onInit() {
    // 获取所有存在注解的
    Map<String, ICmd> cmdMap = SpringUtil.getBeansOfType(ICmd.class);
    Collection<ICmd> ICmds = cmdMap.values();
    cmdMaps = ICmds.stream().collect(Collectors.toMap(ICmd::masterCmdName, x -> x));
  }

  @Override
  public boolean dispatch(Session session) {
    String cmdPrefix = botAutoConfig.getCmdPrefix();

    String actualText = session.getActualText();
    if (StrUtil.isNotEmpty(actualText)) {
      // 判断是否是命令 以指令开始符号的都是
      if (!actualText.startsWith(cmdPrefix)) {
        return true;
      }
      actualText = actualText.replace(cmdPrefix, "");
      handlerCmd(actualText, session);
      // 被指令拦截的不会进行其他的处理
      return false;
    }
    return IBotInterceptor.super.dispatch(session);
  }

  @Override
  public int order() {
    return ORDER;
  }

  /**
   * @MethodName handlerCmd
   *
   * @param cmdStr cmdStr
   * @param session 会话
   * @author yefeng {@date 2024-01-19 16:25:09}
   * @since 1.0
   *     <p>处理程序命令
   */
  @Async
  public void handlerCmd(String cmdStr, Session session) {
    Messages messages = new Messages();

    CmdDTO cmdDTO = CmdTool.decoder(cmdStr);
    // 填入参数 @的
    if (session.getEvent() instanceof GroupMessageEvent groupMessageEvent) {
      List<Segment> atList =
          groupMessageEvent.getMessage().getElements().stream()
              .filter(d -> d.getType().equals("mention"))
              .toList();
      int index = 1;
      for (Segment segment : atList) {
        Map<String, String> params = cmdDTO.getParams();
        String platForm = session.getBot().getPlatForm();
        Mention mention = (Mention) segment;
        BotBind botBind = botUserService.getByPlatformAndPlatformId(platForm, mention.getData().getUserId());
        params.put("@" + index++, String.valueOf(botBind.getId()));
      }
    }
    // 获取主命令
    String mainCmd = cmdDTO.getMainCmd();
    // 获取是否有匹配
    ICmd cmd = cmdMaps.get(mainCmd);
    if (cmd == null) {
      messages.text("指令:{},不存在，请检查输入", mainCmd);
      session.replyMessage(messages);
      return;
    }
    // 判断是否是帮助
    if (cmdDTO.help()) {
      // 获取帮助信息
      messages.text(CmdTool.buildHelp(cmd));
      session.replyMessage(messages);
      return;
    }
    // 获取所有包含子命令的方法
    Method[] methods =
        ReflectUtil.getMethods(cmd.getClass(), x -> x.getAnnotation(SubCmd.class) != null);
    // 获取所有存在的子命令列表 进行匹配
    Map<String, Method> methodMap =
        Arrays.stream(methods)
            .collect(
                Collectors.toMap(
                    d -> {
                      SubCmd subCmd = d.getAnnotation(SubCmd.class);
                      return subCmd.value();
                    },
                    x -> x));

    // 获取子命令匹配 判断是否有key相等
    String subCmd = cmdDTO.getSubCmd();
    Method method = methodMap.get(subCmd);
    // 1 存在子命令匹配 进行参数以及方法调用
    if (method != null) {
      invokeMethod(cmd, method, cmdDTO, session);
    } else {
      // 不存在子命令匹配的 调用默认方法
      Method defaultMethod = methodMap.get(SubCmd.DEFAULT_VALUE);
      invokeMethod(cmd, defaultMethod, cmdDTO, session);
    }
  }

  private void invokeMethod(ICmd cmd, Method method, CmdDTO cmdDTO, Session session) {
    // 获取方法对应的参数
    Parameter[] parameters = method.getParameters();
    List<Object> params = new ArrayList<>();

    List<String> subParams = cmdDTO.getSubParams();
    Map<String, String> paramMap = cmdDTO.getParams();

    for (int i = 0; i < parameters.length; i++) {
      Parameter parameter = parameters[i];
      Class<?> type = parameter.getType();
      // session注入
      if (Session.class.isAssignableFrom(type)) {
        params.add(session);
        continue;
      }
      CmdParams annotation = parameter.getAnnotation(CmdParams.class);
      if (annotation == null) {
        continue;
      }
      // 开始组装参数 如果是从数组获取参数
      String key = annotation.value().replaceAll("-", "");

      // bool注入
      if (Boolean.class.isAssignableFrom(type)) {
        params.add(paramMap.containsKey(key));
        continue;
      }
      boolean required = annotation.required();

      if (key.startsWith("[")) {
        key = key.replace("[", "").replace("]", "");
        int index = Integer.parseInt(key);
        if (required && subParams.size() <= index) {
          session.replyMessage(new Messages().text("参数:{}为必填", key));
          continue;
        }
        params.add(Convert.convert(type, subParams.get(index)));
      } else {
        // 对应命令的参数值
        String value = paramMap.get(key);
        if (StrUtil.isEmpty(value)) {
          if (required && StrUtil.isEmpty(annotation.defaultValue())) {
            session.replyMessage(new Messages().text("参数:{}为必填", key));
            continue;
          }
          // 默认值
          params.add(Convert.convert(type, annotation.defaultValue()));
        } else {
          // 参数值
          params.add(Convert.convert(type, value));
        }
      }
    }
    // 进行调用
    try {
      method.invoke(cmd, params.toArray());
    } catch (Exception e) {
      String errorMessage = "方法调用失败";
      Throwable cause = e.getCause();
      while (cause != null) {
        // 如果是我们定义的异常
        if (cause instanceof BotException botException) {
          errorMessage = botException.getMessage();
          break;
        }
      }
      session.replyMessage(new Messages().text("命令执行错误:\r\n{}", errorMessage));
      log.error("方法调用失败:{}", method.getName(), cause);
    }
  }
}
