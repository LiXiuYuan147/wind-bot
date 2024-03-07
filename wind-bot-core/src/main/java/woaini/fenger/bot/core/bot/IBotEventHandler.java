package woaini.fenger.bot.core.bot;

import woaini.fenger.bot.core.adapter.Adapter;
import woaini.fenger.bot.core.event.action.ActionRequest;
import woaini.fenger.bot.core.event.action.ActionResponse;
import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.eventbus.EventBus;

/**
 * IBot事件处理器
 *
 * @see IBotEventHandler
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public interface IBotEventHandler {


  default void addEvent(Event event){
    EventBus.addEvent(event);
  }

  /**
   * @MethodName action
   *
   * @param actionRequest 行动请求
   * @author yefeng {@date 2024-03-05 11:09:18}
   * @since 1.0
   * @return {@link ActionResponse } 行动 异步执行方法
   */
  ActionResponse action(ActionRequest actionRequest);
}
