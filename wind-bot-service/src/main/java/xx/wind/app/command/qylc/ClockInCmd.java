package xx.wind.app.command.qylc;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.bind.service.BotUserConfigService;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.CmdParams;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.event.segment.Messages;
import woaini.fenger.bot.core.exception.BotAssertTool;
import woaini.fenger.bot.core.exception.enums.BotExceptionType;
import woaini.fenger.bot.core.json.JSONResult;
import woaini.fenger.bot.core.session.Session;
import xx.wind.app.botConfig.qylc.QylcAppConfig;
import xx.wind.app.command.qylc.api.ClockInApi;


/**
 * 时钟输入命令
 *
 * @see woaini.fenger.command.qylc.ClockInCmd
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
public class ClockInCmd implements ICmd {

  @Autowired private BotUserConfigService botUserConfigService;

  @Override
  public String masterCmdName() {
    return "打卡";
  }

  @Override
  public String description() {
    return "偷懒用的公司打卡";
  }

  @SubCmd
  public void clockIn(
      Session session,
      @CmdParams(value = "down", description = "是否下班打卡", defaultValue = "false") Boolean down,
      @CmdParams(value = "u", description = "登录手机号,默认为本机") String loginName) {

    // 获取配置
    QylcAppConfig qylcAppConfig = null;
    if (StrUtil.isEmpty(loginName)) {
      qylcAppConfig = session.getCurrentUserConfig(QylcAppConfig.CONFIG_KEY, QylcAppConfig.class);
    } else {
      qylcAppConfig =
          botUserConfigService.getBotUserLocalConfig(
                  QylcAppConfig.CONFIG_KEY,loginName, QylcAppConfig.class);
      BotAssertTool.isActualTrue(
          qylcAppConfig == null,
          BotExceptionType.BOT_SERVICE,
          "不存在配置:[{}],key:{}",
          QylcAppConfig.CONFIG_KEY,
          loginName);
    }
    BotAssertTool.isActualTrue(
        qylcAppConfig == null,
        BotExceptionType.BOT_SERVICE,
        "不存在配置:[{}]",
        QylcAppConfig.CONFIG_KEY);
    JSONResult login = ClockInApi.login(qylcAppConfig);
    String jwt = (String) login.getPathValue("$.data.jwt");
    JSONResult schedulingUserResult = ClockInApi.schedulingUserList(qylcAppConfig, jwt);

    String schedulingId = null;
    schedulingId = (String) schedulingUserResult.getPathValue("$.data[0].schedulingId");
    if (StrUtil.isEmpty(schedulingId)) {
      JSONResult clockInTypeResult = ClockInApi.getClockInType(qylcAppConfig, jwt);
      schedulingId = (String) clockInTypeResult.getPathValue("$.data.schedulingId");
    }
    BotAssertTool.isActualTrue(
        StrUtil.isEmpty(schedulingId), BotExceptionType.BOT_SERVICE_PARAMS, "排班记录不存在,打卡失败!!!");

    ClockInApi.randomLocation();
    ClockInApi.verifyClockInLog(qylcAppConfig, jwt, schedulingId, !down);
    ClockInApi.saveClockInLog(qylcAppConfig, jwt, schedulingId, !down);
    session.replyMessage(Messages.builder().text((down ? "下" : "上") + "班打卡成功!!"));
  }
}
