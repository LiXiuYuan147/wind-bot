package xx.wind.app.listenner;

import com.plexpt.chatgpt.ChatGPT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.chat.ChatEvent;
import woaini.fenger.bot.core.session.Session;

/**
 * 只会收到 私聊消息 而且是主账户发过来的
 * @see  xx.wind.app.listenner.MessageListener
 * @author yefeng
 * {@date 2024-09-03 16:42:27}
 */@Component
@Slf4j
public class MessageListener {

    @EventListener
    public void onMessage(ChatEvent chatEvent) {
        Session session = chatEvent.getSession();

        String actualText = session.getActualText();
        System.out.println(actualText);
    }
}
