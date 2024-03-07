package woaini.fenger.bot.starter.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import woaini.fenger.bot.core.boot.AppStartupCompletedRunner;

@AutoConfiguration
@Import({AppStartupCompletedRunner.class})
@ComponentScan(basePackages = {"woaini.fenger.bot.core.boot"})
public class WindBotAutoConfig {

}
