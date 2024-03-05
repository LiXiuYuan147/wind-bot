package woaini.fenger.bot.core.adapter;

import org.java_websocket.server.WebSocketServer;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.event.base.ActionRequest;
import woaini.fenger.bot.core.event.base.ActionResponse;
import woaini.fenger.bot.core.event.base.Event;
/**
 * 反向websocket
 *
 * @see woaini.fenger.bot.core.adapter.ReverseWebSocket
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public class ReverseWebSocket extends Adapter{

    private WebSocketServer webSocketServer;

    public ReverseWebSocket(Bot bot) {
        super(bot);
    }
    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public boolean reconnect() {
        return false;
    }

    @Override
    public Event decode(String message) {
        return null;
    }

    @Override
    public String encode(Event event) {
        return null;
    }

    @Override
    public ActionResponse action(ActionRequest actionRequest) {
        return null;
    }
}
