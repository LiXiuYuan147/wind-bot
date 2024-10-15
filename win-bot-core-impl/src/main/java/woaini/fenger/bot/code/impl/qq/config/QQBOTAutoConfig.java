package woaini.fenger.bot.code.impl.qq.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * qq官方机器人自动注入
 * @see woaini.fenger.bot.code.impl.qq.config.QQBOTAutoConfig
 * @author yefeng {@date 2024-09-23 17:55:50}
 */
@ConfigurationProperties(prefix = "wind-bot.qq_bot")
@Getter
@Setter
public class QQBOTAutoConfig {

  private List<QQBOTConfig> bots;

}
