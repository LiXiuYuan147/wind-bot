package woaini.fenger.bot.core.boot;

import cn.hutool.extra.spring.SpringUtil;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 应用程序启动已完成运行程序
 *
 * @see AppStartupCompletedRunner
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
public class AppStartupCompletedRunner implements ApplicationListener<ContextRefreshedEvent> {
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    // 保证只执行一次
    if (event.getApplicationContext().getParent() == null) {
      // 需要执行的方法 扫描所有拥有注解的类 然后执行方法
      Map<String, ApplicationStartupCompleted> beanMap =
          SpringUtil.getBeansOfType(ApplicationStartupCompleted.class);

      Collection<ApplicationStartupCompleted> beans = beanMap.values();
      //调整顺序
      beans =
          beans.stream()
              .sorted(Comparator.comparing(ApplicationStartupCompleted::order))
              .collect(Collectors.toList());
      for (ApplicationStartupCompleted bean : beans) {
        bean.onInit();
      }
    }
  }
}
