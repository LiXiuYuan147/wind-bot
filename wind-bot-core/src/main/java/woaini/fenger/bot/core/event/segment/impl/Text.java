package woaini.fenger.bot.core.event.segment.impl;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woaini.fenger.bot.core.event.segment.Segment;

import java.io.Serializable;

/**
 * 纯文本
 * @see  Text
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */
@Getter
@Setter
public class Text extends Segment implements Serializable {

    private Data data;

    public Text(String text) {
        super("text");
        this.data = new Data(text);
    }

    @NoArgsConstructor
    @lombok.Data
    public static class Data implements Serializable {
        private String text;

        public Data(String text) {
            this.text = text;
        }
    }
}
