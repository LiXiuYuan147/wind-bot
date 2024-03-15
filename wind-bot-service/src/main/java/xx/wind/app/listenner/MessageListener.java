package xx.wind.app.listenner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.session.Session;

@Component
@Slf4j
public class MessageListener {

    @EventListener
    public void onMessage(Session session) {
        String actualText = session.getActualText();

    }
}
