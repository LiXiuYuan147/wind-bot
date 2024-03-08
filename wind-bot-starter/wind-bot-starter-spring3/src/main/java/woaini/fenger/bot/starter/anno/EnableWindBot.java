package woaini.fenger.bot.starter.anno;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.starter.config.WindBotAutoConfig;
import woaini.fenger.bot.starter.config.WindBotImportBeanDefinitionRegistrar;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Import(value = {WindBotAutoConfig.class,WindBotImportBeanDefinitionRegistrar.class,cn.hutool.extra.spring.SpringUtil.class})
public @interface EnableWindBot {
    String[] scanPackages() default {};
}
