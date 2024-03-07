package woaini.fenger.bot.core.bot.enums;

/**
 * 连接类型
 *
 * @see woaini.fenger.bot.core.bot.enums.ConnectType
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public enum ConnectType {

  /**
   * @see ConnectType WS
   */
  WS,
  /**
   * @see ConnectType 反转%ws
   */
  REVERSE_WS,

  /**
   * @see ConnectType HTTP
   */
  HTTP,
  /**
   * @see ConnectType 反向http
   */
  REVERSE_HTTP,
}
