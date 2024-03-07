package woaini.fenger.bot.core.event.meta.impl;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import woaini.fenger.bot.core.event.meta.MetaEvent;

/**
 * 连接配对事件
 *
 * @see ConnectMateEvent
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class ConnectMateEvent extends MetaEvent {

  private Version version;

  public ConnectMateEvent() {
    this.setDetailType("connect");
  }

  /**
   * 版本
   *
   * @see woaini.fenger.bot.core.event.meta.impl.ConnectMateEvent.Version
   * @author yefeng {@code @Date} 2023-05-16 16:50:39
   */
  @NoArgsConstructor
  @Data
  public static class Version implements Serializable {
    /**
     * @see String 实现
     */
    private String impl;

    /**
     * @see String 版本
     */
    private String version;

    /**
     * @see String One机器人版本
     */
    private String onebot_version;
  }
}
