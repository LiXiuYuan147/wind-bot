package woaini.fenger.bot.core.session;

import lombok.Data;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.event.message.impl.GroupMessageEvent;
import woaini.fenger.bot.core.event.message.impl.PrivateMessageEvent;
import woaini.fenger.bot.core.event.segment.Messages;

/**
 * 会话
 *
 * @see woaini.fenger.bot.core.session.Session
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class Session {

    /**
     * @see Bot 消息来自bot
     */
    private Bot bot;

    /**
     * @see Event 事件
     */
    private Event event;

    /**
     * @MethodName replyMessage
     * @param messages 消息
     * @author yefeng
     * {@date 2024-03-08 15:53:07}
     * @since 1.0
     *
     * 回复消息
     */
    public void replyMessage(Messages messages){
        switch (event){
            case PrivateMessageEvent privateMessageEvent->{
                bot.internal().sendPrivateMessage(privateMessageEvent.getUserId(), messages);
            }
            case GroupMessageEvent groupMessageEvent->{
                bot.internal().sendPrivateMessage(groupMessageEvent.getGroupId(), messages);
            }
            case null, default -> {}
        }
    }

    /**
     * @MethodName replyMessage
     * @param text 文本
     * @author yefeng
     * {@date 2024-03-08 15:53:05}
     * @since 1.0
     *
     * 回复消息
     */
    public void replyMessage(String text){
        replyMessage(Messages.builder().text(text));
    }

    public String getActualText(){
        return this.event.getAltMessage();
    }
}
