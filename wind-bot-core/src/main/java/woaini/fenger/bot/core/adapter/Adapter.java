package woaini.fenger.bot.core.adapter;

import lombok.Data;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.event.action.ActionRequest;
import woaini.fenger.bot.core.event.action.ActionResponse;
import woaini.fenger.bot.core.event.base.Event;

/**
 * 转接器 负责链接以及转换消息
 *
 * @see woaini.fenger.bot.core.adapter.Adapter
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public abstract class Adapter {

  protected Bot bot;

  public Adapter(Bot bot) {
    this.bot = bot;
  }

  /**
   * @MethodName connect
   *
   * @author yefeng {@date 2024-03-05 11:23:25}
   * @since 1.0
   * @return boolean 连接 返回是否链接成功
   */
  public abstract boolean connect();

  /**
   * @MethodName reconnect
   *
   * @author yefeng {@date 2024-03-05 14:07:51}
   * @since 1.0
   * @return boolean 重新连接
   */
  public abstract boolean reconnect();

  /**
   * @MethodName decode
   *
   * @param message 讯息
   * @author yefeng {@date 2024-03-05 11:24:54}
   * @since 1.0
   * @return {@link Event } 解码 将原生消息转换为Event
   */
  public abstract Event decode(String message);

  /**
   * @MethodName encode
   *
   * @param action 请求
   * @author yefeng {@date 2024-03-05 11:25:17}
   * @since 1.0
   * @return {@link String } 将event转换为原生消息
   */
  public abstract String encode(ActionRequest action);

  /**
   * @MethodName action
   *
   * @param actionRequest 行动请求
   * @author yefeng {@date 2024-03-05 11:09:18}
   * @since 1.0
   * @return {@link ActionResponse } 行动 异步执行方法
   */
  public abstract ActionResponse action(ActionRequest actionRequest);

  public abstract void close();
}
