package woaini.fenger.bot.core.bot.config;

import lombok.Data;

/**
 * 反向%ws配置
 *
 * @see woaini.fenger.bot.core.bot.config.ReverseWsConfig
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class ReverseWsConfig extends BotConfig {

  /**
   * @see Integer 端口
   */
  private Integer port;
}
