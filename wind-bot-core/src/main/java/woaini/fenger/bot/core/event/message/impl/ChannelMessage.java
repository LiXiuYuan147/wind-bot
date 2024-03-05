package woaini.fenger.bot.core.event.message.impl;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import woaini.fenger.bot.core.event.message.Message;

/**
 * 两集群组接口 Guild-Channel
 *
 * @see woaini.fenger.bot.core.event.message.impl.ChannelMessage
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class ChannelMessage extends Message {

  public ChannelMessage() {
    this.setDetailType("channel");
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
