package woaini.fenger.bot.core.command.system;

import cn.hutool.system.SystemUtil;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.event.segment.Messages;
import woaini.fenger.bot.core.session.Session;

/**
 * 运行时信息命令
 *
 * @see woaini.fenger.bot.core.command.system.RuntimeInfoCmd
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
public class RuntimeInfoCmd implements ICmd {
  @Override
  public String masterCmdName() {
    return "运行状态";
  }

  @Override
  public String description() {
    return "获取主机内存情况";
  }

  @SubCmd
  public void run(Session session) {
    session.replyMessage(new Messages().text(SystemUtil.getRuntimeInfo().toString()));
  }
}
