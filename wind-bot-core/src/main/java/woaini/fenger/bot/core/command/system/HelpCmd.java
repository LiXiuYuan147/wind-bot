package woaini.fenger.bot.core.command.system;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.bot.config.BotAutoConfig;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.command.utils.CmdTool;
import woaini.fenger.bot.core.event.segment.Messages;
import woaini.fenger.bot.core.session.Session;

/**
 * 帮助命令
 *
 * @see woaini.fenger.bot.core.command.system.HelpCmd
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
@AllArgsConstructor
public class HelpCmd implements ICmd {

  private final BotAutoConfig botAutoConfig;

  @Override
  public String masterCmdName() {
    return "帮助";
  }

  @Override
  public String description() {
    return "可以给你一些帮助";
  }

  @SubCmd
  public void help(Session session) {
    session.replyMessage(new Messages().text(CmdTool.buildHelp(botAutoConfig.getCmdPrefix())));
  }
}
