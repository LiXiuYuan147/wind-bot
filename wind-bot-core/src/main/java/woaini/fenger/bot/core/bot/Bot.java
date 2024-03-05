package woaini.fenger.bot.core.bot;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import woaini.fenger.bot.core.bot.config.BotConfig;
import woaini.fenger.bot.core.bot.enums.BotStatus;
import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.internal.Internal;

/**
 * 所有bot的基类
 *
 * @see woaini.fenger.bot.core.bot.Bot
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Slf4j
@Data
public abstract class Bot<T extends BotConfig> implements IBotEventHandler {

  /**
   * @see BotStatus 状态
   */
  private BotStatus status;

  /**
   * @see T 配置
   */
  private T config;

  /**
   * @see String 自身id 例如qq号
   */
  private String selfId;

  /**
   * @see String 对应的平台 所有平台以小写为主
   */
  private String platForm;

  /**
   * @see String 协议 实际对接的协议 比如利用的那个第三方
   */
  private String agreement;

  /**
   * @see Internal 方法
   */
  private Internal internal;

  /**
   * @see BlockingQueue<Event> 事件队列 所有Bot接受到的事件都放到这个队列里 然后由消费线程进行消费
   */
  private BlockingQueue<Event> EVENT_QUEUE = new LinkedBlockingQueue<>(1024);


  public Bot(T config) {
    this.config = config;
    this.status = BotStatus.OFFLINE;
  }

  public void online() {
    this.status = BotStatus.ONLINE;
  }

  public void offline() {
    this.status = BotStatus.OFFLINE;
  }

  @Override
  public void addEvent(Event event) {
    EVENT_QUEUE.add(event);
  }

  @Override
  public Event getEvent() {
    try{
      return EVENT_QUEUE.take();
    }catch (Exception ex){
      log.error("获取事件失败", ex);
      return null;
    }
  }
}
