package woaini.fenger.bot.code.impl.onebot11.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 一个bot 11自动配置
 *
 * @see woaini.fenger.bot.code.impl.onebot11.config.OneBot11AutoConfig
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@ConfigurationProperties(prefix = "wind-bot.ont-bot-11")
@Getter
@Setter
public class OneBot11AutoConfig {

    private List<OneBot11Config> bots;

}
