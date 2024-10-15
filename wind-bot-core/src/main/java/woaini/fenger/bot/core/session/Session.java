package woaini.fenger.bot.core.session;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import woaini.fenger.bot.core.bind.domain.BotBind;
import woaini.fenger.bot.core.bind.domain.BotRole;
import woaini.fenger.bot.core.bind.service.BotUserConfigService;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.event.base.Event;
import woaini.fenger.bot.core.event.message.impl.GroupMessageEvent;
import woaini.fenger.bot.core.event.message.impl.PrivateMessageEvent;
import woaini.fenger.bot.core.event.segment.Messages;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void setBind(BotBind bind) {
        this.bind = bind;
        authCmds = new HashSet<>();
        for (BotRole role : bind.getBotUser().getRoles()) {
            authCmds.addAll(role.getCmds());
        }
    }

    private BotBind bind;

    private Set<String> authCmds;

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
                bot.internal().sendGroupMessage(groupMessageEvent.getGroupId(), messages);
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

    public <R> R getCurrentUserConfig(String configKey, Class<R> rClass) {
        BotUserConfigService bean = SpringUtil.getBean(BotUserConfigService.class);
        return bean.getBotUserConfig(this.bind.getBotUser().getId().toString(), configKey, rClass);
    }

    public <R> R getUserConfig(String id,String configKey, Class<R> rClass) {
        BotUserConfigService bean = SpringUtil.getBean(BotUserConfigService.class);
        return bean.getBotUserConfig(id, configKey, rClass);
    }
}
