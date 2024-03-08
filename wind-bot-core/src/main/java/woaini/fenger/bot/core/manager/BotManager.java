package woaini.fenger.bot.core.manager;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.boot.ApplicationStartupCompleted;
import woaini.fenger.bot.core.boot.IBotAutoRegister;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.bot.BotKey;
import woaini.fenger.bot.core.dispatcher.BotDispatcher;
import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.eventbus.EventBus;
import woaini.fenger.bot.core.session.Session;
import woaini.fenger.bot.core.utils.ThreadTool;

/**
 * BOT管理器 所有bot的顶层容器
 *
 * @see woaini.fenger.bot.core.manager.BotManager
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Slf4j
@FieldNameConstants
public class BotManager
    implements ApplicationListener<ContextClosedEvent>, ApplicationStartupCompleted {
  /**
   * @see Map<BotKey, Bot> 存储所有的bot
   */
  private static final Map<BotKey, Bot> BOT_INSTANCE_MAP = new HashMap<>();

  public List<String> packages;

  public void setPackages(List<String> packages) {
    this.packages = packages;
  }

  /**
   * @MethodName registerBot
   *
   * @param bot bot
   * @author yefeng {@date 2024-03-04 18:00:13}
   * @since 1.0
   *     <p>注册机器人 注册后 如果是自动登录的，会进行自动登录程序
   */
  private void registerBot(Bot bot) {
    if (BOT_INSTANCE_MAP.containsKey(bot.botKey())) {
      return;
    }
    BOT_INSTANCE_MAP.put(bot.botKey(), bot);
    log.info("注册机器人: {}", bot.botKey());
    Boolean autoStart = bot.getConfig().getAutoStart();
    if (autoStart) {
      bot.startConnect();
    }
  }

  @Override
  public void onApplicationEvent(ContextClosedEvent event) {
    for (Bot bot : BOT_INSTANCE_MAP.values()) {
      bot.close();
    }
  }

  @Override
  public void onInit() {
    // 扫码包下面的 IBotAutoRegister
    Map<String, IBotAutoRegister> beans = SpringUtil.getBeansOfType(IBotAutoRegister.class);
    beans.forEach(
        (k, bean) -> {
          bean.autoRegister().forEach(this::registerBot);
        });
    // 启动消费线程
    BotManagerRunner botManagerRunner = new BotManagerRunner();
    ThreadTool.run(botManagerRunner);
  }

  /**
   * 机器人管理器用于分发事件
   *
   * @see woaini.fenger.bot.core.manager.BotManager.BotManagerRunner
   * @author yefeng {@code @Date} 2023-05-16 16:50:39
   */
  public static class BotManagerRunner implements Runnable {

    private BotDispatcher botDispatcher;

    public BotManagerRunner() {
      this.botDispatcher = SpringUtil.getBean(BotDispatcher.class);
    }

    @Override
    public void run() {
      log.info("BotManagerRunner is running...");
      while (true) {
        Session session = EventBus.getEvent();
        Event event = session.getEvent();
        if (event == null) {
          continue;
        }
        log.info(
            "事件发放,{}-{}:{}",
            session.getBot().agreement(),
            session.getBot().getSelfId(),
            JSONObject.toJSONString(event));
        // 进行任务分发操作 超时10秒
        ThreadTool.submitTask(
            () -> {
              botDispatcher.dispatch(session);
            },
            10);
      }
    }
  }
}
