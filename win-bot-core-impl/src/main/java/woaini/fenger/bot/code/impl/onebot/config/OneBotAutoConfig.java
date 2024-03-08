package woaini.fenger.bot.code.impl.onebot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 一个机器人自动配置
 * @see  woaini.fenger.bot.code.impl.onebot.config.OneBotAutoConfig
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */
@ConfigurationProperties(prefix = "wind-bot.ont-bot")
@Getter
@Setter
public class OneBotAutoConfig {

    private List<OneBotConfig> bots;
}
