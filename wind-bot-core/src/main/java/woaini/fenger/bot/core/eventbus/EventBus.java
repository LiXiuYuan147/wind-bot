package woaini.fenger.bot.core.eventbus;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import woaini.fenger.bot.core.event.base.Event;

/**
 * 事件母线
 *
 * @see woaini.fenger.bot.core.eventbus.EventBus
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@UtilityClass
@Slf4j
public class EventBus {

  /**
   * @see BlockingQueue < Event > 事件队列 所有Bot接受到的事件都放到这个队列里 然后由消费线程进行消费
   */
  private static final BlockingQueue<Event> EVENT_QUEUE = new LinkedBlockingQueue<>(1024);

  /**
   * @MethodName addEvent
   *
   * @param event 活动
   * @author yefeng {@date 2024-03-07 09:23:43}
   * @since 1.0
   *     <p>添加事件
   */
  public static void addEvent(Event event) {
    EVENT_QUEUE.add(event);
  }

  /**
   * @MethodName getEvent
   *
   * @author yefeng {@date 2024-03-07 09:23:39}
   * @since 1.0
   * @return {@link Event } 获取事件 阻塞的方式
   */
  public static Event getEvent() {
    try {
      return EVENT_QUEUE.take();
    } catch (Exception ex) {
      log.error("获取事件失败", ex);
      return null;
    }
  }
}
