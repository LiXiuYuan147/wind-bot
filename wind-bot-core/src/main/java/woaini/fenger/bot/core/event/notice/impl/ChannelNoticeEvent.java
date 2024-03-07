package woaini.fenger.bot.core.event.notice.impl;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import woaini.fenger.bot.core.event.notice.NoticeEvent;

/**
 * 2级群组通知
 *
 * @see ChannelNoticeEvent
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class ChannelNoticeEvent extends NoticeEvent {

    /**
     * @see String 用户id
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * @see String 群组id
     */
    @JSONField(name = "guild_id")
    private String guildId;

    /**
     * @see String 频道id
     */
    @JSONField(name = "channel_id")
    private String channelId;

    @JSONField(name = "operator_id")
    private String operatorId;
}
