package woaini.fenger.bot.core.event.message.impl;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import woaini.fenger.bot.core.event.message.MessageEvent;

/**
 * 单用户消息 Private / Direct
 *
 * @see PrivateMessageEvent
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class PrivateMessageEvent extends MessageEvent {

    /**
     * @see String 用户id
     */
    @JSONField(name = "user_id")
    private String userId;

    public PrivateMessageEvent() {
        this.setDetailType("private");
    }
}
