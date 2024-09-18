package woaini.fenger.bot.core.chat;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import woaini.fenger.bot.core.session.Session;

/**
 * 聊天的事件
 *
 * @see woaini.fenger.bot.core.chat.ChatEvent
 * @author yefeng {@date 2024-09-03 16:20:42}
 */
@Getter
public class ChatEvent extends ApplicationEvent {

  private Session session;

  public ChatEvent(Object source, Session session) {
    super(source);
    this.session = session;
  }
}
