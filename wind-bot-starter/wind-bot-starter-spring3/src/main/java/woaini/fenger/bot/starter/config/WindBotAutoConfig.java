package woaini.fenger.bot.starter.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import woaini.fenger.bot.core.boot.AppStartupCompletedRunner;
import woaini.fenger.bot.core.boot.IBotAutoRegister;

@AutoConfiguration
@ComponentScan(basePackages = {"woaini.fenger.bot.core","woaini.fenger.bot.code.impl.onebot"})
public class WindBotAutoConfig {

}
