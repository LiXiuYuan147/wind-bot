package woaini.fenger.bot.core.command.system;

import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.CmdParams;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.event.segment.Messages;
import woaini.fenger.bot.core.session.Session;


/**
 * 测试命令测试命令
 *
 * @see EchoCmd
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
public class EchoCmd implements ICmd {
  @Override
  public String masterCmdName() {
    return "echo";
  }

  @Override
  public String description() {
    return "复读机";
  }

  @SubCmd
  public void reply(Session session,
                    @CmdParams(value = "[0]", description = "复读的话",required = true) String text) {
    session.replyMessage(Messages.builder().text(text));
  }
}
