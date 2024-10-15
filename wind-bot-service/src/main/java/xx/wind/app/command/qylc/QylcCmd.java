package xx.wind.app.command.qylc;

import cn.hutool.core.util.StrUtil;
import org.jetbrains.annotations.Nullable;
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
import woaini.fenger.bot.core.utils.TokenCacheTool;
import xx.wind.app.botConfig.qylc.QylcAppConfig;
import xx.wind.app.command.qylc.api.QylcApi;

/**
 * 公司相关
 *
 * @see QylcCmd
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
public class QylcCmd implements ICmd {

  @Autowired private BotUserConfigService botUserConfigService;

  @Override
  public String masterCmdName() {
    return "公司";
  }

  @Override
  public String description() {
    return "公司内部相关接口";
  }

  @Override
  public boolean auth() {
    return true;
  }

  @SubCmd(value = "打卡", auth = true)
  public void clockIn(
      Session session,
      @CmdParams(value = "down", description = "是否下班打卡", defaultValue = "false") Boolean down,
      @CmdParams(value = "u", description = "登录手机号,默认为本机") String loginName) {

    // 获取配置
    QylcAppConfig qylcAppConfig = this.getQylcAppConfig(session, loginName);

    String tokenKey = QylcAppConfig.CONFIG_KEY + ":" + qylcAppConfig.getLoginName();
    String jwt =
        TokenCacheTool.getAndLoadWithKey(
            tokenKey,
            (token) -> QylcApi.verifyActivityStatistics(qylcAppConfig,token),
            () -> {
              JSONResult login = QylcApi.login(qylcAppConfig);
              return login.getPathValue("$.data.jwt").toString();
            });
    JSONResult schedulingUserResult = QylcApi.schedulingUserList(qylcAppConfig, jwt);
    String schedulingId = null;
    schedulingId = (String) schedulingUserResult.getPathValue("$.data[0].schedulingId");
    if (StrUtil.isEmpty(schedulingId)) {
      JSONResult clockInTypeResult = QylcApi.getClockInType(qylcAppConfig, jwt);
      schedulingId = (String) clockInTypeResult.getPathValue("$.data.schedulingId");
    }
    BotAssertTool.isActualTrue(
        StrUtil.isEmpty(schedulingId), BotExceptionType.BOT_SERVICE_PARAMS, "排班记录不存在,打卡失败!!!");

    QylcApi.randomLocation();
    QylcApi.verifyClockInLog(qylcAppConfig, jwt, schedulingId, !down);
    QylcApi.saveClockInLog(qylcAppConfig, jwt, schedulingId, !down);
    session.replyMessage(Messages.builder().text((down ? "下" : "上") + "班打卡成功!!"));
  }

  @Nullable
  private QylcAppConfig getQylcAppConfig(Session session, String loginName) {
    QylcAppConfig qylcAppConfig = null;
    if (StrUtil.isEmpty(loginName)) {
      qylcAppConfig = session.getCurrentUserConfig(QylcAppConfig.CONFIG_KEY, QylcAppConfig.class);
    } else {
      qylcAppConfig =
          botUserConfigService.getBotUserLocalConfig(
              QylcAppConfig.CONFIG_KEY, loginName, QylcAppConfig.class);
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
    return qylcAppConfig;
  }

  @SubCmd(value = "菜单", auth = true)
  public void foodMenu(Session session) {
    QylcAppConfig qylcAppConfig = this.getQylcAppConfig(session, null);
    String tokenKey = STR."\{QylcAppConfig.CONFIG_KEY}:\{qylcAppConfig.getLoginName()}";
    String jwt =
      TokenCacheTool.getAndLoadWithKey(
        tokenKey,
        (token) -> QylcApi.verifyActivityStatistics(qylcAppConfig,token),
        () -> {
          JSONResult login = QylcApi.login(qylcAppConfig);
          return login.getPathValue("$.data.jwt").toString();
        });
    JSONResult jsonResult = QylcApi.foodMenu(qylcAppConfig, jwt);

    StringBuilder stringBuilder = new StringBuilder();

    String yearWeek = jsonResult.getPathValue("$.data.yearWeek").toString();
    stringBuilder.append("本周周次:").append(yearWeek).append("\n");
    //时间段
    String startTime = jsonResult.getPathValue("$.data.startTime").toString();
    String endTime = jsonResult.getPathValue("$.data.endTime").toString();
    stringBuilder.append("时间段:").append(startTime).append("到").append(endTime).append("\n");


    String image = jsonResult.getPathValue("$.data.image").toString();

    Messages messages = Messages.builder().text(stringBuilder.toString()).image(image);
    session.replyMessage(messages);
  }
}
