package woaini.fenger.bot.core.dispatcher.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.chat.ChatEvent;
import woaini.fenger.bot.core.dispatcher.IBotInterceptor;
import woaini.fenger.bot.core.event.message.MessageEvent;
import woaini.fenger.bot.core.event.message.impl.PrivateMessageEvent;
import woaini.fenger.bot.core.session.Session;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatInterceptor implements IBotInterceptor {

  private final ApplicationEventPublisher eventPublisher;

  @Override
  public boolean preDispatch(Session session) {
    return session.getEvent() instanceof MessageEvent;
  }

  @Override
  public boolean dispatch(Session session) {
    // 仅 是主要账号 私聊消息

    boolean privateEvent = session.getEvent() instanceof PrivateMessageEvent;
    if (!privateEvent) {
      return true;
    }
    if (!((PrivateMessageEvent) session.getEvent()).getUserId().equals("2901427129")) {
      return true;
    }
    eventPublisher.publishEvent(new ChatEvent(this, session));
    return IBotInterceptor.super.dispatch(session);
  }

  @Override
  public int order() {
    return CmdInterceptor.ORDER + 1;
  }
}
