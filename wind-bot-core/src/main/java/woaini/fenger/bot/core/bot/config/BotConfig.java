package woaini.fenger.bot.core.bot.config;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import woaini.fenger.bot.core.bot.Bot;

import java.io.Serializable;
import java.util.Map;

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

  /**
   * @see Class<? extends Bot> 实例
   */
  private Class<? extends Bot> instance;

  Map<String, Object> params;

  Map<String, String> headers;
}
