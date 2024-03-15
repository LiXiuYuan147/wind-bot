package woaini.fenger.bot.starter.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import woaini.fenger.bot.core.boot.AppStartupCompletedRunner;
import woaini.fenger.bot.core.boot.IBotAutoRegister;

@AutoConfiguration
@ComponentScan(basePackages = {"woaini.fenger.bot.core","woaini.fenger.bot.code.impl"})
@EnableJpaRepositories(basePackages = {"woaini.fenger.bot.core","woaini.fenger.bot.code.impl"})
@EntityScan(basePackages = {"woaini.fenger.bot.core","woaini.fenger.bot.code.impl"})
public class WindBotAutoConfig {

}
