package woaini.fenger.bot.core.event.segment;

import lombok.Data;

/**
 * 消息段
 * @see  woaini.fenger.bot.core.event.segment.Segment
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */
@Data
public abstract class Segment {
    /**
     * @see String 类型
     */
    String type;

    public Segment(String type) {
        this.type = type;
    }
}
