package woaini.fenger.bot.core.event.message.impl;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import woaini.fenger.bot.core.event.message.MessageEvent;

/**
 * 单级群组消息 Group
 *
 * @see GroupMessageEvent
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class GroupMessageEvent extends MessageEvent {

    public GroupMessageEvent() {
        this.setDetailType("group");
    }

    @Override
    public String getSendUserId() {
        return userId;
    }

    /**
     * @see String 用户id
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * @see String 用户id
     */
    @JSONField(name = "group_id")
    private String groupId;
}
