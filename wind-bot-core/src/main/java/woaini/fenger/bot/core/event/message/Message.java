package woaini.fenger.bot.core.event.message;

import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.event.enums.EventType;

/**
 * 消息
 *
 * @see woaini.fenger.bot.core.event.message.Message
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public class Message extends Event {
  public Message() {
    this.setType(EventType.message);
  }
}
