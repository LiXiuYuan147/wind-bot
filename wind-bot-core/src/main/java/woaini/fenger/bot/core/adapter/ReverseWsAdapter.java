package woaini.fenger.bot.core.adapter;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.bot.config.BotConfig;
import woaini.fenger.bot.core.event.action.ActionRequest;
import woaini.fenger.bot.core.event.action.ActionResponse;
import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.event.enums.EventType;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * 反向websocket
 *
 * @see ReverseWsAdapter
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Slf4j
public abstract class ReverseWsAdapter extends Adapter {

  private WebSocketServer webSocketServer;

  private WebSocket botWebSocket;

  public ReverseWsAdapter(Bot bot) {
    super(bot);
    init();
  }

  public void init() {

    BotConfig config = this.bot.getConfig();
    Object reverseWsPort = config.getExtra("reverseWsPort");
    webSocketServer =
        new WebSocketServer(new InetSocketAddress(Convert.toInt(reverseWsPort))) {
          @Override
          public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
            botWebSocket = webSocket;
          }

          @Override
          public void onClose(WebSocket webSocket, int i, String s, boolean b) {
              log.info("反向ws链接关闭,{}-{}", bot.getPlatForm(), bot.getSelfId());
          }

          @Override
          public void onMessage(WebSocket webSocket, String s) {
            log.info("收到消息,{}-{}：{}", bot.getSelfId(), bot.getPlatForm(), s);
            Event event = decode(s);
            // 元事件不进行放入队列
            if (event != null && !event.getType().equals(EventType.meta)) {
              bot.addEvent(decode(s));
            }
          }

          @Override
          public void onError(WebSocket webSocket, Exception e) {
              log.info("反向ws异常,{}-{}", bot.getPlatForm(), bot.getSelfId(), e);
          }

          @Override
          public void onStart() {
            log.info("反向ws启动成功,{}-{},端口:{}", bot.getPlatForm(), bot.getSelfId(), getPort());
          }
        };
  }

  @Override
  public boolean connect() {
    webSocketServer.start();
    return false;
  }

  @Override
  public boolean reconnect() {
    return false;
  }

  @Override
  public ActionResponse action(ActionRequest actionRequest) {
    String data = encode(actionRequest);
    botWebSocket.send(data);
    return null;
  }

  @Override
  public void close() {
    try {
      webSocketServer.stop();
    } catch (Exception ignore) {

    }
  }
}