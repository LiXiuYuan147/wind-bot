package woaini.fenger.bot.core.dispatcher.impl;

import com.mysql.cj.protocol.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.bind.domain.BotBind;
import woaini.fenger.bot.core.bind.service.BotUserService;
import woaini.fenger.bot.core.dispatcher.IBotInterceptor;
import woaini.fenger.bot.core.event.message.MessageEvent;
import woaini.fenger.bot.core.event.message.impl.GroupMessageEvent;
import woaini.fenger.bot.core.session.Session;


/**
 * 用户绑定拦截器
 *
 * @see UserBindInterceptor
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
@AllArgsConstructor
public class UserBindInterceptor implements IBotInterceptor {

    private BotUserService botUserService;


    @Override
    public boolean preDispatch(Session session) {
        return session.getEvent() instanceof MessageEvent;
    }
    @Override
    public boolean dispatch(Session session) {


        MessageEvent messageEvent = (MessageEvent) session.getEvent();

        String sendUserId = messageEvent.getSendUserId();
        String platForm = session.getBot().getPlatForm();
        String selfId = session.getBot().getSelfId();
        //TODO 名字信息
        String name = null;
        String nickname = null;

        //群聊必须at
        if (session.getEvent() instanceof GroupMessageEvent groupMessageEvent){
            if (!groupMessageEvent.getMessage().isAt(selfId)){
                return true;
            }
        }
        //获取名字信息
        BotBind botBind = botUserService.saveOrUpdate(sendUserId, platForm, name, nickname);
        session.setBind(botBind);
        return IBotInterceptor.super.dispatch(session);
    }

    @Override
    public int order() {
        return CmdInterceptor.ORDER - 1;
    }
}
