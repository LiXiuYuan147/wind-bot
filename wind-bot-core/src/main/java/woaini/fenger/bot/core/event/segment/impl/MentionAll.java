package woaini.fenger.bot.core.event.segment.impl;

import woaini.fenger.bot.core.event.segment.Segment;

import java.io.Serializable;

/**
 * 提及所有人
 * @see  woaini.fenger.bot.core.event.segment.impl.MentionAll
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */
public class MentionAll extends Segment implements Serializable {

    public MentionAll() {
        super("mention_all");
    }
}
