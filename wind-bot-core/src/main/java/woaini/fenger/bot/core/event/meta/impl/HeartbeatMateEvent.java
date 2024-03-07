package woaini.fenger.bot.core.event.meta.impl;

import lombok.Data;
import woaini.fenger.bot.core.event.meta.MetaEvent;

@Data
public class HeartbeatMateEvent extends MetaEvent {

    /**
     * @see Long 单位毫秒
     */
    private Long interval;

    public HeartbeatMateEvent() {
        this.setDetailType("heartbeat");
    }
}
