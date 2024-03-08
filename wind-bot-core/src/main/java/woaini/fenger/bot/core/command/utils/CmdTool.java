package woaini.fenger.bot.core.command.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.CmdParams;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.command.dto.CmdDTO;
import woaini.fenger.bot.core.dispatcher.impl.CmdInterceptor;
/**
 * CMD工具
 * @see  woaini.fenger.bot.core.command.utils.CmdTool
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */@UtilityClass
public class CmdTool {

  public static final String CMD_PREFIX = "-";
  private static final Pattern CMD_REGEX =
      Pattern.compile(
          "(-[a-zA-Z]+(\\s)+['\"]?[\\u4e00-\\u9fa5a-zA-Z0-9]+['\"]?(\\s)?)|(-+[a-zA-Z]+)|(\\S+)");

  public CmdDTO decoder(String cmd) {
    CmdDTO cmdDTO = new CmdDTO();
    cmdDTO.setMainCmd("");

    List<String> subParams = new ArrayList<>();

    // 正则匹配
    List<String> params = new ArrayList<>();
    Matcher matcher = CMD_REGEX.matcher(cmd);
    while (matcher.find()) {
      params.add(matcher.group());
    }
    HashMap<String, String> paramMap = new HashMap<>();
    for (String param : params) {
      // 解析参数
      if (param.startsWith(CMD_PREFIX)) {
        // 获取第一个空格的位置 然后拆分为2个字符串
        int index = param.indexOf(" ");
        String key = null;
        String value = "";
        if (index == -1) {
          // 标识只有key
          key = param.trim().replace(CMD_PREFIX, "");
          paramMap.put(key, null);
          continue;
        }
        // 如果存在空格 判断有没有value
        key = param.substring(0, index).trim().replace(CMD_PREFIX, "");
        value = param.substring(index + 1).trim();
        paramMap.put(key, value);
      } else {
        if (StrUtil.isEmpty(cmdDTO.getMainCmd())) {
          cmdDTO.setMainCmd(param);
        } else {
          subParams.add(param);
        }
      }
    }
    cmdDTO.setSubParams(subParams);
    if (CollUtil.isNotEmpty(subParams)) {
      cmdDTO.setSubCmd(subParams.get(0));
    }
    cmdDTO.setParams(paramMap);
    return cmdDTO;
  }

  public String buildHelp(ICmd cmd) {
    StringBuilder sqlBuilder = new StringBuilder();
    Method[] methods =
        ReflectUtil.getMethods(cmd.getClass(), x -> x.getAnnotation(SubCmd.class) != null);

    sqlBuilder
        .append("指令: ")
        .append(cmd.masterCmdName())
        .append(" ")
        .append(cmd.description())
        .append("\r\n");

    for (Method method : methods) {
      SubCmd subCmd = method.getAnnotation(SubCmd.class);
      if (subCmd.value().equals(SubCmd.DEFAULT_VALUE)) {
        sqlBuilder.append("\t").append("默认:").append(subCmd.description()).append("\r\n");
      } else {
        sqlBuilder
            .append("子指令")
            .append("\r\n")
            .append(subCmd.value())
            .append(":")
            .append(subCmd.description())
            .append("\r\n");
      }
      // 解析参数
      Parameter[] parameters = method.getParameters();
      for (Parameter parameter : parameters) {
        CmdParams cmdParams = parameter.getAnnotation(CmdParams.class);
        if (cmdParams == null) {
          continue;
        }
        Class<?> type = parameter.getType();
        sqlBuilder.append("\t\t");
        if (Boolean.class.isAssignableFrom(type)) {
          sqlBuilder.append("--");
        } else {
          sqlBuilder.append("-");
        }
        sqlBuilder.append(cmdParams.value());
        if (cmdParams.required()) {
          sqlBuilder.append("*");
        }
        sqlBuilder.append(":").append(cmdParams.description());
        sqlBuilder.append("\r\n");
      }
      sqlBuilder.append("\r\n");
    }
    return sqlBuilder.toString();
  }

  public String buildHelp() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("当前可用指令有\r\n");
    for (ICmd value : CmdInterceptor.cmdMaps.values()) {
      stringBuilder
          .append("#")
          .append(value.masterCmdName())
          .append(":")
          .append(value.description())
          .append("\r\n");
    }
    return stringBuilder.toString();
  }
}
