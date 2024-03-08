package woaini.fenger.bot.core.bot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "wind-bot")
@Getter
@Setter
public class BotAutoConfig {

  /** 命令前缀 */
  private String cmdPrefix = "#";
}
