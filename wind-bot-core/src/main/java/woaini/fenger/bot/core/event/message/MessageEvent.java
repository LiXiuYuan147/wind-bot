package woaini.fenger.bot.core.event.message;

import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.event.enums.EventType;

/**
 * 消息
 *
 * @see MessageEvent
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public abstract class MessageEvent extends Event {
  public MessageEvent() {
    this.setType(EventType.message);
  }
}
