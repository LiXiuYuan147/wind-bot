package woaini.fenger.bot.core.dispatcher;

import cn.hutool.extra.spring.SpringUtil;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.boot.ApplicationStartupCompleted;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.session.Session;

/**
 * BOT调度器 综合调度器
 *
 * @see BotDispatcher
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
public class BotDispatcher implements ApplicationStartupCompleted {

  private Collection<IBotInterceptor> beans;

  public void dispatch(Session session) {
    Event event = session.getEvent();
    if (event == null){
      return;
    }
    for (IBotInterceptor bean : beans) {
      boolean predDispatch = bean.preDispatch(session);
      if (!predDispatch){
        continue;
      }
      boolean dispatch = bean.dispatch(session);
      if (!dispatch){
        return;
      }
    }
  }

  @Override
  public void onInit() {
    //获取所有的调度器 依次执行
    Map<String, IBotInterceptor> botDispatcherMap = SpringUtil.getBeansOfType(IBotInterceptor.class);
    beans = botDispatcherMap.values();
    //调整顺序
    beans =
            beans.stream()
                    .sorted(Comparator.comparing(IBotInterceptor::order))
                    .collect(Collectors.toList());
  }
}
