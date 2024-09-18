package xx.wind.app.command.history;

import cn.hutool.core.lang.Console;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.CmdParams;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.dispatcher.impl.HistoryInterceptor;
import woaini.fenger.bot.core.event.message.MessageEvent;
import woaini.fenger.bot.core.session.Session;

import java.util.Queue;

/**
 * 测回消息
 *
 * @see xx.wind.app.command.history.HistoryCmd
 * @author yefeng {@date 2024-09-18 18:08:39}
 */
@Component
public class HistoryCmd implements ICmd {


  @Override
  public String masterCmdName() {
    return "查看撤回";
  }

  @Override
  public String description() {
    return "查看某人测回的消息";
  }

  @SubCmd
  public void execute(Session session,@CmdParams(value = "@1", description = "第一个艾特的用户") Integer userId) {

    Integer id = session.getBind().getBotUser().getId();
    if (id != 2){
      session.replyMessage("没有权限使用命令哦");
      return;
    }
    Queue<MessageEvent> messageEvents = HistoryInterceptor.MESSAGE_BACK_CACHE.get(userId);
    if (messageEvents != null){
      MessageEvent messageEvent = messageEvents.poll();
      session.replyMessage("悄悄撤回了:" + messageEvent.getAltMessage());
    }else {
      session.replyMessage("已经没有撤回的消息啦");
    }
  }
}
