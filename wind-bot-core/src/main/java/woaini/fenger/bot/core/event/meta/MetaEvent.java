package woaini.fenger.bot.core.event.meta;

import lombok.Data;
import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.event.enums.EventType;

/**
 * 元事件
 *
 * @see woaini.fenger.bot.core.event.meta.MetaEvent
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class MetaEvent extends Event {

    public MetaEvent() {
        this.setType(EventType.meta);
    }
}
