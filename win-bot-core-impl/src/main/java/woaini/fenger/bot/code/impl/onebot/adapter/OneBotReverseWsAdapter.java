package woaini.fenger.bot.code.impl.onebot.adapter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import woaini.fenger.bot.core.adapter.ReverseWsAdapter;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.event.action.ActionRequest;
import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.event.enums.EventType;
import woaini.fenger.bot.core.event.message.impl.ChannelMessageEvent;
import woaini.fenger.bot.core.event.message.impl.GroupMessageEvent;
import woaini.fenger.bot.core.event.message.impl.PrivateMessageEvent;
import woaini.fenger.bot.core.event.meta.impl.ConnectMateEvent;
import woaini.fenger.bot.core.event.meta.impl.HeartbeatMateEvent;
import woaini.fenger.bot.core.event.meta.impl.StatusUpdateMetaEvent;
import woaini.fenger.bot.core.event.notice.impl.ChannelNoticeEvent;
import woaini.fenger.bot.core.event.notice.impl.GroupNoticeEvent;
import woaini.fenger.bot.core.event.notice.impl.PrivateNoticeEvent;

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
    String typeString = source.getString("type");
    if (StrUtil.isEmpty(typeString)){
      return event;
    }
    EventType type = EventType.valueOf(typeString);
    String detailType = source.getString("detail_type");
    switch (type) {
      case meta -> {
        switch (detailType) {
          case "connect" -> {
            event = JSONObject.parseObject(message,ConnectMateEvent.class);
          }
          case "heartbeat" -> {
            event = JSONObject.parseObject(message,HeartbeatMateEvent.class);
          }
          case "status_update" -> {
            event = JSONObject.parseObject(message,StatusUpdateMetaEvent.class);
          }
          case null, default -> {}
        }
      }
      case message -> {
        switch (detailType) {
          case "private" -> {
            event = JSONObject.parseObject(message,PrivateMessageEvent.class);
          }
          case "group" -> {
            event =  JSONObject.parseObject(message,GroupMessageEvent.class);
          }
          case "channel" -> {
            event = JSONObject.parseObject(message,ChannelMessageEvent.class);
          }
          case null, default -> {}
        }
      }
      case notice -> {
        switch (detailType) {
          case "private" -> {
            event = JSONObject.parseObject(message, PrivateNoticeEvent.class);
          }
          case "group" -> {
            event =  JSONObject.parseObject(message, GroupNoticeEvent.class);
          }
          case "channel" -> {
            event = JSONObject.parseObject(message, ChannelNoticeEvent.class);
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
