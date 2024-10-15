package woaini.fenger.bot.code.impl.qq.config;

import woaini.fenger.bot.code.impl.qq.QQBOT;
import woaini.fenger.bot.core.bot.config.BotConfig;

/**
 * qq官方机器人配置
 * @see woaini.fenger.bot.code.impl.qq.config.QQBOTConfig
 * @author yefeng {@date 2024-09-23 17:54:13}
 */
public class QQBOTConfig extends BotConfig {

  private String appId;
  private String token;

  public QQBOTConfig() {
    super();
    this.setAgreement(QQBOT.NAME);
  }
}
