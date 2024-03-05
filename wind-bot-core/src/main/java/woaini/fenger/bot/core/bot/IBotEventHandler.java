package woaini.fenger.bot.core.bot;

import woaini.fenger.bot.core.event.base.ActionRequest;
import woaini.fenger.bot.core.event.base.ActionResponse;
import woaini.fenger.bot.core.event.base.Event;

/**
 * IBot事件处理器
 *
 * @see IBotEventHandler
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public interface IBotEventHandler {

  /**
   * @MethodName getEvent
   *
   * @author yefeng {@date 2024-03-05 11:08:01}
   * @since 1.0
   * @return {@link Event } 获取事件 阻塞方法 从bot获取一个事件进行处理 没有事件則阻塞
   */
  Event getEvent();

  void addEvent(Event event);
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
