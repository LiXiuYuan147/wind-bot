package xx.wind.app.command.xiaomi;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.CmdParams;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.event.segment.Messages;
import woaini.fenger.bot.core.exception.BotAssertTool;
import woaini.fenger.bot.core.exception.enums.BotExceptionType;
import woaini.fenger.bot.core.json.JSONResult;
import woaini.fenger.bot.core.session.Session;
import xx.wind.app.botConfig.xiaomi.XiaoMiSportsConfig;
import xx.wind.app.command.xiaomi.api.XiaoMiSportsApi;


/**
 * 小米体育
 *
 * @see woaini.fenger.command.xiaomi.XiaoMiSportsCmd
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
@Slf4j
public class XiaoMiSportsCmd implements ICmd {

  @Override
  public String masterCmdName() {
    return "微信刷步";
  }

  @Override
  public String description() {
    return "走了，但是又没走";
  }

  @SubCmd
  public void run(
          Session session, @CmdParams(value = "s", description = "步数", required = true) Integer steps) {

    // 获取配置
    XiaoMiSportsConfig userConfig =
        session.getCurrentUserConfig(XiaoMiSportsConfig.CONFIG_KEY, XiaoMiSportsConfig.class);
    BotAssertTool.isActualTrue(
        userConfig == null,
        BotExceptionType.BOT_SERVICE,
        "不存在配置:[{}]",
        XiaoMiSportsConfig.CONFIG_KEY);

    String code = XiaoMiSportsApi.login(userConfig);
    BotAssertTool.isActualTrue(StrUtil.isEmpty(code), BotExceptionType.BOT_SERVICE, "登录失败,无法获取code");
    //获取token
    JSONResult tokenResult = XiaoMiSportsApi.getToken(userConfig.getLoginName(), code);
    String loginToken = (String) tokenResult.getPathValue("$.token_info.login_token");
    String userid = (String) tokenResult.getPathValue("$.token_info.user_id");
    BotAssertTool.isActualTrue(StrUtil.isEmpty(loginToken), BotExceptionType.BOT_SERVICE, "登录失败,无法获取token");
    //获取appToken
    JSONResult appTokenResult = XiaoMiSportsApi.getAppToken(loginToken);
    String appToken = (String) appTokenResult.getPathValue("$.token_info.app_token");
    BotAssertTool.isActualTrue(StrUtil.isEmpty(appToken), BotExceptionType.BOT_SERVICE, "登录失败,无法获取换取appToken");
    JSONResult changeStepsResult = XiaoMiSportsApi.changeSteps(userid, appToken, steps);
    Integer statusCode = (Integer) changeStepsResult.getPathValue("$.code");
    if (statusCode == 1){
      session.replyMessage(Messages.builder().text("步数修改成功，请刷新微信查看"));
    }else {
      session.replyMessage(Messages.builder().text("步数修改失败"));
    }
  }
}
