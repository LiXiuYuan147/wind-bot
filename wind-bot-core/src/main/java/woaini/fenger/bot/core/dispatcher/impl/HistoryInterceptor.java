package woaini.fenger.bot.core.dispatcher.impl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.BoundedPriorityQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.bind.domain.BotBind;
import woaini.fenger.bot.core.bind.service.BotUserService;
import woaini.fenger.bot.core.dispatcher.IBotInterceptor;
import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.event.message.MessageEvent;
import woaini.fenger.bot.core.event.notice.NoticeEvent;
import woaini.fenger.bot.core.session.Session;

import java.util.Comparator;
import java.util.Queue;

/**
 * 历史消息处理器 主要是用来防范测回消息用 需要再命令拦截器之前
 *
 * @see woaini.fenger.bot.core.dispatcher.impl.HistoryInterceptor
 * @author yefeng {@date 2024-09-18 16:53:26}
 */
@Component
@RequiredArgsConstructor
public class HistoryInterceptor implements IBotInterceptor {

  private final BotUserService botUserService;


  /**
   * @see TimedCache<Integer, Queue<MessageEvent>> 消息缓存 最大缓存100条消息 最多2分钟
   */
  public static TimedCache<Integer, Queue<MessageEvent>> MESSAGE_CACHE =
      CacheUtil.newTimedCache(1000 * 60 * 2);

  public static final int MAX_QUEUE_SIZE = 100;

  /**
   * @see TimedCache<Integer, Queue<MessageEvent>> 测回消息信息缓存 超过10分钟没看消失
   */
  public static TimedCache<Integer, Queue<MessageEvent>> MESSAGE_BACK_CACHE =
      CacheUtil.newTimedCache(1000 * 60 * 10);

  @Override
  public int order() {
    return CmdInterceptor.ORDER - 1;
  }

  @Override
  public boolean dispatch(Session session) {

    // 获取绑定用户信息
    Integer userId = session.getBind().getBotUser().getId();

    // 如果是通知消息 拦截测回的
    if (session.getEvent() instanceof NoticeEvent noticeEvent) {
      if (noticeEvent.getNoticeType().equals("group_recall")
          || noticeEvent.getNoticeType().equals("friend_recall")) {
        // 获取缓存里面已经测回的消息
        Queue<MessageEvent> queue = MESSAGE_BACK_CACHE.get(userId);
        if (queue == null) {
          queue =
            new BoundedPriorityQueue<>(
              10, Comparator.comparingLong(Event::getTime).reversed());
        }
        Queue<MessageEvent> messageEventQueue = MESSAGE_CACHE.get(userId);

        MessageEvent messageEvent =
          messageEventQueue.stream()
                .filter(d -> d.getMessageId().equals(session.getEvent().getMessageId()))
                .findFirst()
                .orElse(null);

        if (messageEvent != null){
          queue.add(messageEvent);
          String sendUserId = messageEvent.getSendUserId();
          String platForm = session.getBot().getPlatForm();
          BotBind botBind = botUserService.getByPlatformAndPlatformId(platForm, sendUserId);
          MESSAGE_BACK_CACHE.put(botBind.getId(), queue);
        }
      }
    }

    if (!(session.getEvent() instanceof MessageEvent saveEvent)) {
      return true;
    }

    Queue<MessageEvent> queue = MESSAGE_CACHE.get(userId);
    if (queue == null) {
      queue =
          new BoundedPriorityQueue<>(
              MAX_QUEUE_SIZE, Comparator.comparingLong(Event::getTime).reversed());
    }
    queue.add(saveEvent);
    MESSAGE_CACHE.put(userId, queue);
    return IBotInterceptor.super.dispatch(session);
  }

  @Override
  public boolean preDispatch(Session session) {
    return IBotInterceptor.super.preDispatch(session);
  }
}
