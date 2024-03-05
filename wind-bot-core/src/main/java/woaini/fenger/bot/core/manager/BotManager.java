package woaini.fenger.bot.core.manager;

import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.bot.BotKey;
import woaini.fenger.bot.core.bot.config.BotConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * BOT管理器 所有bot的顶层容器
 *
 * @see woaini.fenger.bot.core.manager.BotManager
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public class BotManager {
  /**
   * @see Map<BotKey, Bot> 存储所有的bot
   */
  private static final Map<BotKey, Bot> BOT_MAP = new HashMap<>();

  /**
   * @MethodName registerBot
   * @param botConfig bot配置
   * @author yefeng
   * {@date 2024-03-04 18:00:13}
   * @since 1.0
   *
   * 注册机器人 注册后 如果是自动登录的，会进行自动登录程序
   */
  public void registerBot(BotConfig botConfig) {

    //获取对应的平台和协议对应的机器人

  }
}
