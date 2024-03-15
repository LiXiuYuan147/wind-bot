package xx.wind.app.command.hitokoto;

import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.CmdParams;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.session.Session;
import xx.wind.app.command.hitokoto.api.HitokotoApi;
import xx.wind.app.command.hitokoto.api.HitokotoResDTO;


@Component
public class HitokotoCmd implements ICmd {
  @Override
  public String masterCmdName() {
    return "一言";
  }

  @Override
  public String description() {
    return "获取一句话";
  }

  @SubCmd
  public void run(
      Session session,
      @CmdParams(
              value = "c",
              description =
                  "获取的类型\n   * a\t动画\n"
                      + "   * b\t漫画\n"
                      + "   * c\t游戏\n"
                      + "   * d\t文学\n"
                      + "   * e\t原创\n"
                      + "   * f\t来自网络\n"
                      + "   * g\t其他\n"
                      + "   * h\t影视\n"
                      + "   * i\t诗词\n"
                      + "   * j\t网易云\n"
                      + "   * k\t哲学\n"
                      + "   * l\t抖机灵")
          String type) {

    HitokotoResDTO resDTO = HitokotoApi.get(type);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(resDTO.getHitokoto()).append("\r\n");
    stringBuilder
        .append("——")
        .append(resDTO.getFrom_who())
        .append("<")
        .append(resDTO.getFrom())
        .append(">");
    session.replyMessage(stringBuilder.toString());
  }
}
