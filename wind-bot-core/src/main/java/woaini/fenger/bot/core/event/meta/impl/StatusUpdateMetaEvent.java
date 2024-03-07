package woaini.fenger.bot.core.event.meta.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import woaini.fenger.bot.core.event.meta.MetaEvent;

/**
 * 状态更新元事件
 *
 * @see StatusUpdateMetaEvent
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class StatusUpdateMetaEvent extends MetaEvent {

  private Status status;

  public StatusUpdateMetaEvent() {
    this.setDetailType("status_update");
  }

  @NoArgsConstructor
  @Data
  public static class Status implements Serializable {
    private Boolean good;
    private List<Bots> bots;

    @NoArgsConstructor
    @Data
    public static class Bots implements Serializable {
      /**
       * @see Self selfx
       */
      private Self selfX;

      /**
       * @see Boolean 在线
       */
      private Boolean online;

      /**
       * @see Map<String, String> 额外
       */
      private Map<String, String> extra;

      @NoArgsConstructor
      @Data
      public static class Self implements Serializable {
        /**
         * @see String 站台
         */
        private String platform;

        /**
         * @see String 用户ID
         */
        private String user_id;
      }
    }
  }
}
