package woaini.fenger.bot.core.event.message.impl;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import woaini.fenger.bot.core.event.message.Message;

/**
 * 单级群组消息 Group
 *
 * @see GroupMessage
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class GroupMessage extends Message {

    public GroupMessage() {
        this.setDetailType("group");
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
