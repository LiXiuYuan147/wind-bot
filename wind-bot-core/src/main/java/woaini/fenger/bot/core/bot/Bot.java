package woaini.fenger.bot.core.bot;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import woaini.fenger.bot.core.adapter.Adapter;
import woaini.fenger.bot.core.bot.config.BotConfig;
import woaini.fenger.bot.core.bot.enums.BotStatus;
import woaini.fenger.bot.core.event.action.ActionRequest;
import woaini.fenger.bot.core.event.action.ActionResponse;
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
   * @see String 协议 实际对接的协议 比如利用的那个第三方 不同的协议对应不同的机器人类型
   */
  public abstract String agreement();

  public BotKey botKey(){
    return new BotKey(selfId, platForm);
  };

  public Bot(T config) {
    this.config = config;
    this.selfId = config.getSelfId();
    this.platForm = config.getPlatForm();
    this.status = BotStatus.OFFLINE;
  }

  public void online() {
    this.status = BotStatus.ONLINE;
  }

  public void offline() {
    this.status = BotStatus.OFFLINE;
  }

  public void startWorker() {
    if (this.sendAdapter() == null || this.receiveAdapter() == null){
      return;
    }
    if (this.receiveAdapter() == this.sendAdapter()){
      this.receiveAdapter().connect();
    }else {
      this.receiveAdapter().connect();
      this.sendAdapter().connect();
    }
  }

  /**
   * @MethodName close
   *
   * @author yefeng {@date 2024-03-06 17:39:54}
   * @since 1.0
   *     <p>需要实现关闭时干嘛
   */
  public abstract void close();

  /**
   * @MethodName receiveAdapter
   *
   * @author yefeng {@date 2024-03-06 16:18:25}
   * @since 1.0
   * @return {@link Adapter } 接收适配器
   */
  public abstract Adapter receiveAdapter();

  /**
   * @MethodName sendAdapter
   *
   * @author yefeng {@date 2024-03-06 16:18:27}
   * @since 1.0
   * @return {@link Adapter } 发送适配器
   */
  public abstract Adapter sendAdapter();

  /**
   * @MethodName internal
   *
   * @author yefeng {@date 2024-03-06 16:18:29}
   * @since 1.0
   * @return {@link Internal } 内部
   */
  public abstract Internal internal();

  @Override
  public ActionResponse action(ActionRequest actionRequest) {
    return this.sendAdapter().action(actionRequest);
  }
}
