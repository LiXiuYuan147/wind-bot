package woaini.fenger.bot.core.event.notice.impl;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import woaini.fenger.bot.core.event.notice.NoticeEvent;

/**
 * 群组相关通知
 *
 * @see GroupNoticeEvent
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class GroupNoticeEvent extends NoticeEvent {

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

    @JSONField(name = "operator_id")
    private String operatorId;
}
