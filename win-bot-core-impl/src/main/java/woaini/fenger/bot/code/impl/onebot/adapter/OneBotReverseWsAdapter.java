package woaini.fenger.bot.code.impl.onebot.adapter;

import com.alibaba.fastjson2.JSONObject;
import woaini.fenger.bot.core.adapter.ReverseWsAdapter;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.event.action.ActionRequest;
import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.event.enums.EventType;
import woaini.fenger.bot.core.event.meta.impl.ConnectMateEvent;
import woaini.fenger.bot.core.event.meta.impl.HeartbeatMateEvent;
import woaini.fenger.bot.core.event.meta.impl.StatusUpdateMetaEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * onebot反向WS适配器
 *
 * @see woaini.fenger.bot.code.impl.onebot.adapter.OneBotReverseWsAdapter
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public class OneBotReverseWsAdapter extends ReverseWsAdapter {

  private static final Map<String, Object> DECODE_MAP = new HashMap<>();

  static {
  }

  public OneBotReverseWsAdapter(Bot bot) {
    super(bot);
  }

  @Override
  public Event decode(String message) {

    Event event = null;
    // 获取事件类型
    JSONObject source = JSONObject.parseObject(message);
    EventType type = EventType.valueOf(source.getString("type"));
    String detailType = source.getString("detail_type");
    switch (type) {
      case meta -> {
        switch (detailType) {
          case "connect" -> {
            event = source.toJavaObject(ConnectMateEvent.class);
          }
          case "heartbeat" -> {
            event = source.toJavaObject(HeartbeatMateEvent.class);
          }
          case "status_update" -> {
            event = source.toJavaObject(StatusUpdateMetaEvent.class);
          }
          case null, default -> {}
        }
      }
      default -> {}
    }

    return event;
  }

  @Override
  public String encode(ActionRequest action) {
    return JSONObject.toJSONString(action);
  }
}
