package woaini.fenger.bot.code.impl.onebot11.adapter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSONArray;
import woaini.fenger.bot.core.event.base.Self;
import woaini.fenger.bot.core.event.message.MessageEvent;
import woaini.fenger.bot.core.event.message.impl.GroupMessageEvent;
import woaini.fenger.bot.core.event.message.impl.PrivateMessageEvent;
import woaini.fenger.bot.core.event.meta.impl.HeartbeatMateEvent;
import woaini.fenger.bot.core.event.segment.Messages;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import woaini.fenger.bot.core.adapter.WsAdapter;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.event.action.ActionRequest;
import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.event.meta.MetaEvent;
import woaini.fenger.bot.core.event.meta.impl.ConnectMateEvent;
import woaini.fenger.bot.core.event.segment.Segment;
import woaini.fenger.bot.core.event.segment.impl.Mention;

import java.util.List;
import java.util.Map;

/**
 * 一个bot 11正向ws适配器
 *
 * @see OneBot11WsAdapter
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public class OneBot11WsAdapter extends WsAdapter {

  public OneBot11WsAdapter(Bot bot) {
    super(bot);
  }

  @Override
  public Event decode(String message) {
    Event event = null;
    // 获取事件上报类型
    JSONObject source = JSONObject.parseObject(message);

    String postType = source.getString("post_type");
    if (StrUtil.isEmpty(postType)){
      return event;
    }
    event = new Event();
    //消息id
    String messageId = source.getString("message_id");
    event.setId(messageId);
    //发送消息的来源
    Self self = new Self();
    self.setPlatform("qq");
    String selfId = source.getString("self_id");
    self.setUserId(selfId);
    event.setSelf(self);

    Long time = source.getLong("time");
    event.setTime(time);
    event.setMessageId(messageId);

    String rawMessage = source.getString("raw_message");
    event.setAltMessage(rawMessage);
    switch (postType){
      case "meta_event" -> event = decodeMetaEvent(source,event);
      case "message" -> event = decodeMessageEvent(source,event);
      default -> {}
    }
    return event;
  }


  @Override
  public String encode(ActionRequest action) {

    //转换message
    Map<String, Object> params = action.getParams();
    Messages message = (Messages) params.get("message");
    JSONArray jsonArray = this.encodeMessages(message);
    params.put("message",jsonArray);
    return JSONObject.toJSONString(action);
  }

  @Override
  public void close() {

  }

  /**
   * @MethodName decodeMessageEvent
   * @param source 源
   * @param baseEvent 基础事件
   * @author yefeng
   * {@date 2024-05-08 15:29:14}
   * @since 1.0
   * @return {@link MessageEvent }
   * 解码消息事件
   */
  public MessageEvent decodeMessageEvent(JSONObject source, Event baseEvent) {

    //消息类型 私聊 群里
    String messageType = source.getString("message_type");
    JSONArray messageJsonArray = source.getJSONArray("message");

    Messages messages = this.decodeMessages(messageJsonArray);
    baseEvent.setMessage(messages);

    switch (messageType){
      case "private" -> {
        PrivateMessageEvent privateMessageEvent = new PrivateMessageEvent();
        BeanUtil.copyProperties(baseEvent,privateMessageEvent);
        //发送人
        String userId = source.getString("user_id");
        privateMessageEvent.setUserId(userId);
        return privateMessageEvent;
      }
      case "group" -> {
        GroupMessageEvent groupMessageEvent = new GroupMessageEvent();
        BeanUtil.copyProperties(baseEvent,groupMessageEvent);
        String userId = source.getString("user_id");
        groupMessageEvent.setUserId(userId);
        String groupId = source.getString("group_id");
        groupMessageEvent.setGroupId(groupId);
        return groupMessageEvent;
      }
      case null, default -> {
        return null;
      }
    }
  }


  /**
   * @MethodName decodeMetaEvent
   * @param source 源
   * @param baseEvent 基础事件
   * @author yefeng
   * {@date 2024-05-08 15:25:27}
   * @since 1.0
   * @return {@link MetaEvent }
   * 解码Meta事件
   */
  public MetaEvent decodeMetaEvent(JSONObject source,Event baseEvent) {
    String metaEventType = source.getString("meta_event");

    MetaEvent metaEvent = new MetaEvent();
    BeanUtil.copyProperties(baseEvent,metaEvent);

    switch (metaEventType){
      case "lifecycle" -> {
        ConnectMateEvent connectMateEvent = new ConnectMateEvent();
        BeanUtil.copyProperties(baseEvent,connectMateEvent);
        return connectMateEvent;
      }
      case "heartbeat" -> {
        HeartbeatMateEvent heartbeatMateEvent = new HeartbeatMateEvent();
        BeanUtil.copyProperties(baseEvent,heartbeatMateEvent);
        return heartbeatMateEvent;
      }
      case null, default -> {
        return null;
      }
    }
  }


  /**
   * @MethodName builderMessages
   * @param messageJsonArray 消息json数组
   * @author yefeng
   * {@date 2024-05-08 15:43:28}
   * @since 1.0
   * @return {@link Messages }
   * 构建者消息 目前就解析纯文本和at
   */
  public Messages decodeMessages(JSONArray messageJsonArray){

    Messages messages = new Messages();

    for (int i = 0; i < messageJsonArray.size(); i++) {
      JSONObject messageJsonObject = messageJsonArray.getJSONObject(i);
      String type = messageJsonObject.getString("type");
      JSONObject data = messageJsonObject.getJSONObject("data");
      switch (type){
        case "text" -> {
          String text = data.getString("text");
          messages.text(text);
        }
        case "at" -> {
          String userId = data.getString("qq");
          messages.at(userId);
        }
        case null, default -> {
          return messages;
        }
      }
    }
    return messages;
  }

  /**
   * @MethodName encodeMessages
   * @param messages 消息
   * @author yefeng
   * {@date 2024-05-08 16:35:09}
   * @since 1.0
   * @return {@link JSONArray }
   * 编码消息
   */
  public JSONArray encodeMessages(Messages messages){

    JSONArray jsonArray = new JSONArray();

    List<Segment> elements = messages.getElements();
    if (CollUtil.isEmpty(elements)){
      return jsonArray;
    }
    for (Segment element : elements) {
      String type = element.getType();
      switch (type){
        case "mention" -> {
          Mention mention = (Mention) element;
          JSONObject jsonObject = new JSONObject();
          jsonObject.put("type","at");
          JSONObject data = new JSONObject();
          data.put("qq",mention.getData().getUserId());
          jsonObject.put("data",data);
          jsonArray.add(jsonObject);
        }
        default -> {jsonArray.add(element);}
      }
    }
    return jsonArray;
  }
}
