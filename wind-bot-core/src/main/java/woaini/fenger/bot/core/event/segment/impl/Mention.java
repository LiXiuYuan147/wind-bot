package woaini.fenger.bot.core.event.segment.impl;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woaini.fenger.bot.core.event.segment.Segment;

import java.io.Serializable;

/**
 * 提及（即 @）
 *
 * @see woaini.fenger.bot.core.event.segment.impl.Mention
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Getter
@Setter
public class Mention extends Segment implements Serializable {

  private Data data;

  public Mention(String userId) {
    super("mention");
    this.data = new Data(userId);
  }

  @NoArgsConstructor
  @lombok.Data
  public static class Data implements Serializable {

    @JSONField(name = "user_id")
    private String userId;

    public Data(String userId) {
      this.userId = userId;
    }
  }
}
