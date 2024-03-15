package xx.wind.app.command.amap;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.CmdParams;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.event.segment.Messages;
import woaini.fenger.bot.core.session.Session;
import xx.wind.app.command.amap.api.AmapApi;
import xx.wind.app.command.amap.dto.WeatherDTO;


@Component
public class AmapCmd implements ICmd {

  @Override
  public String masterCmdName() {
    return "天气";
  }

  @Override
  public String description() {
    return "查天气";
  }

  @SubCmd
  public void run(Session session, @CmdParams(value = "-c") String cityCode) {

    if (StrUtil.isEmpty(cityCode)) {
      cityCode = "520115";
    }
    WeatherDTO weather = AmapApi.weather(cityCode);
    boolean boolSuccess = weather.getStatus().equals("1");

    WeatherDTO.Lives lives = weather.getLives().get(0);
    if (boolSuccess) {
      session.replyMessage(
          Messages.builder()
              .text(
                  "天气状况："+lives.getWeather()+"\n"
                      + "城市： "
                      + lives.getCity()
                      + "\n"
                      + "实时气温： "
                      + lives.getTemperature()
                      + "\n"
                      + "风向： "
                      + lives.getWinddirection()
                      + "\n"
                      + "湿度： "
                      + lives.getHumidity()
                      + "\n"));
      return;
    }
    session.replyMessage(new Messages().text("查询失败"));
  }
}
