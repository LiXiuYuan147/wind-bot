package woaini.fenger.bot.starter.config;

import cn.hutool.core.collection.ListUtil;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import woaini.fenger.bot.core.manager.BotManager;
import woaini.fenger.bot.starter.anno.EnableWindBot;

/**
 * wind bot导入bean定义注册器 主要用于注册自定义的bot
 *
 * @see woaini.fenger.bot.starter.config.WindBotImportBeanDefinitionRegistrar
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public class WindBotImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

  /**
   * @see String 默认程序包 这里放着系统默认实现的协议这样的
   */
  public static final String DEFAULT_PACKAGES = "woaini.fenger.bot.code.impl";

  @Override
  public void registerBeanDefinitions(
      AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

    Map<String, Object> attr =
        annotationMetadata.getAnnotationAttributes(EnableWindBot.class.getName());
    String[] packArr = (String[]) attr.get("scanPackages");
    List<String> packages = ListUtil.toList(packArr);
    packages.add(DEFAULT_PACKAGES);
    BeanDefinitionBuilder bdb = BeanDefinitionBuilder.rootBeanDefinition(BotManager.class);
    bdb.addPropertyValue(BotManager.Fields.packages, packages);
    beanDefinitionRegistry.registerBeanDefinition(
        BotManager.class.getName(), bdb.getBeanDefinition());
  }
}
