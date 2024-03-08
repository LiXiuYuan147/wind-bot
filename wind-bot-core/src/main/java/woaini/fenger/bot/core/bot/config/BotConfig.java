package woaini.fenger.bot.core.bot.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.bot.enums.ConnectType;

/**
 * 机器人配置
 *
 * @see woaini.fenger.bot.core.bot.config.BotConfig
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public abstract class BotConfig implements Serializable {

  /**
   * @see String 自身id 例如qq号
   */
  private String selfId;
  /**
   * @see String 对应的平台 所有平台以小写为主
   */
  private String platForm;
  /**
   * @see String 协议 实际对接的协议
   */
  private String agreement;
  private String wsHost;
  private Integer reverseWsPort;
  private String httpHost;
  private Integer reverseHttpPort;
  /**
   * @see ConnectType 接收连接类型
   */
  private ConnectType receiveConnectType;
  /**
   * @see ConnectType 发送链接类型
   */
  private ConnectType sendConnectType;
  private String accessToken;
  private Boolean autoStart;

  public BotConfig() {
    this.autoStart = false;
  }
}
