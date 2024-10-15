package woaini.fenger.bot.code.impl.qq.adapter;

import woaini.fenger.bot.core.adapter.WsAdapter;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.event.action.ActionRequest;
import woaini.fenger.bot.core.event.base.Event;

/**
 * QQ官方机器人用  websocket 用于接受时间和消息
 * @see QQBOTWsAdapter
 * @author yefeng {@date 2024-09-23 17:45:56}
 */
public class QQBOTWsAdapter extends WsAdapter {


  public QQBOTWsAdapter(Bot bot) {
    super(bot);
  }

  @Override
  public Event decode(String message) {
    return null;
  }

  @Override
  public String encode(ActionRequest action) {
    return "";
  }

  @Override
  public void close() {

  }
}
