package woaini.fenger.bot.core.exception.enums;

import lombok.Getter;

/**
 * 僵尸程序异常类型
 *
 * @see woaini.fenger.bot.core.exception.enums.BotExceptionType
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Getter
public enum BotExceptionType {

  /**
   * @see BotExceptionType 机器人配置
   */
  BOT_CONFIG("BOT配置"),
  /**
   * @see BotExceptionType BOT服务参数
   */
  BOT_SERVICE_PARAMS("bot业务参数"),

  BOT_SERVICE("bot业务异常"),

  /**
   * @see BotExceptionType 机器人RPC参数
   */
  BOT_RPC_PARAMS("bot RPC异常"),
  ;

  /**
   * @see String 名字
   */
  private String name;

  BotExceptionType(String name) {
    this.name = name;
  }
}
