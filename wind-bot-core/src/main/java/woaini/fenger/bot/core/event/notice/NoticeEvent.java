package woaini.fenger.bot.core.event.notice;

import lombok.Data;
import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.event.enums.EventType;

/**
 * 通知
 *
 * @see NoticeEvent
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public abstract class NoticeEvent extends Event {

    public NoticeEvent() {
        this.setType(EventType.notice);
    }
}
