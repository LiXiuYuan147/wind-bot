package woaini.fenger.bot.core.adapter;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.bot.config.BotConfig;
import woaini.fenger.bot.core.event.action.ActionRequest;
import woaini.fenger.bot.core.event.action.ActionResponse;
import woaini.fenger.bot.core.event.base.Event;
import java.net.URI;


/**
 * WS适配器 正向链接适配器
 * @see  WsAdapter
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */
@Slf4j
public abstract class WsAdapter extends Adapter {

    private WebSocketClient webSocketClient;

    private BotConfig botConfig;

    public WsAdapter(Bot bot) {
        super(bot);
        this.botConfig = bot.getConfig();
        init();
    }

    public void init(){

        //获取ws链接
        String wsHost = botConfig.getWsHost();
        URI uri = null;
        try {
            uri = new URI(wsHost);
        } catch (Exception e) {
        }
        if (uri == null) {
            log.error("socketClient创建uri失败:{}", uri);
            return;
        }
        webSocketClient =
                new WebSocketClient(uri, new Draft_6455(), null) {
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        bot.online();
                        log.info("socket链接成功:{},{}", botConfig.getSelfId(), botConfig.getPlatForm());
                    }

                    @Override
                    public void onMessage(String s) {
                        log.debug("socket原始数据:{},{},{}",s, botConfig.getSelfId(), botConfig.getPlatForm());
                        Event decode = decode(s);
                        if (decode != null){
                            bot.addEvent(decode);
                        }
                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        bot.offline();
                        log.info("socket链接关闭:{},{}", botConfig.getSelfId(), botConfig.getPlatForm());
                    }

                    @Override
                    public void onError(Exception e) {
                        log.error("socket链接异常:{},{}", botConfig.getSelfId(), botConfig.getPlatForm(),e);
                    }
                };
    }

    @Override
    public boolean connect() {
        webSocketClient.connect();
        return false;
    }

    @Override
    public boolean reconnect() {
        webSocketClient.reconnect();
        return false;
    }

    @Override
    public ActionResponse action(ActionRequest actionRequest) {
        String data = encode(actionRequest);
        webSocketClient.send(data);
        return null;
    }
}
