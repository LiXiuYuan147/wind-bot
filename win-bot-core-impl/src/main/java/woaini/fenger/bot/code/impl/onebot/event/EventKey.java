package woaini.fenger.bot.code.impl.onebot.event;

import lombok.Data;
import woaini.fenger.bot.core.event.enums.EventType;

import java.util.Objects;

@Data
public class EventKey {

  /**
   * @see EventType 事件类型
   */
  private EventType eventType;

  /**
   * @see String 详图类型
   */
  private String detailType;

  /**
   * @see String 子类型
   */
  private String subType;

  public EventKey(EventType eventType, String detailType, String subType) {
    this.eventType = eventType;
    this.detailType = detailType;
    this.subType = subType;
  }

  public static EventKey of(EventType eventType, String detailType, String subType){
    return new EventKey(eventType, detailType, subType);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    EventKey eventKey = (EventKey) object;
    return eventType == eventKey.eventType && Objects.equals(detailType, eventKey.detailType) && Objects.equals(subType, eventKey.subType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventType, detailType, subType);
  }
}
