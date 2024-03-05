package woaini.fenger.bot.core.event.enums;

/**
 * 事件类型
 *
 * @see woaini.fenger.bot.core.event.enums.EventType
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public enum EventType {
  /**
   * @see EventType 元事件
   */
  meta,
  /**
   * @see EventType 消息事件
   */
  message,
  /**
   * @see EventType 通知事件
   */
  notice,
  /**
   * @see EventType 请求事件
   */
  request
}
