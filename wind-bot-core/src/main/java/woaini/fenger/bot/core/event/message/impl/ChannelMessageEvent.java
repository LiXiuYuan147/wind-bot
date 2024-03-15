package woaini.fenger.bot.core.event.message.impl;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import woaini.fenger.bot.core.event.message.MessageEvent;

/**
 * 两集群组接口 Guild-Channel
 *
 * @see ChannelMessageEvent
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class ChannelMessageEvent extends MessageEvent {

  public ChannelMessageEvent() {
    this.setDetailType("channel");
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
  @JSONField(name = "guild_id")
  private String guildId;

  /**
   * @see String 用户id
   */
  @JSONField(name = "channel_id")
  private String channelId;
}
