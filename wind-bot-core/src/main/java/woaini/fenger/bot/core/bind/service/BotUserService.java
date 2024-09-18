package woaini.fenger.bot.core.bind.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woaini.fenger.bot.core.bind.domain.BotBind;
import woaini.fenger.bot.core.bind.domain.BotUser;
import woaini.fenger.bot.core.bind.respository.BotBindRepository;
import woaini.fenger.bot.core.bind.respository.BotUserRepository;
import woaini.fenger.bot.core.exception.BotAssertTool;
import woaini.fenger.bot.core.exception.enums.BotExceptionType;


/**
 * BOT用户服务
 *
 * @see woaini.fenger.bot.core.bind.service.BotUserService
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Service
@AllArgsConstructor
public class BotUserService {

  private BotBindRepository botBindRepository;

  private BotUserRepository botUserRepository;

  /**
   * @MethodName saveOrUpdate
   *
   * @param userId 用户ID
   * @param pLatForm 后一种形式
   * @param userName 用户名
   * @param nickName 昵称
   * @author yefeng {@date 2024-01-29 16:02:35}
   * @since 1.0
   * @return {@link BotBind } 保存或更新
   */
  @Transactional(rollbackFor = Exception.class)
  public BotBind saveOrUpdate(String userId, String pLatForm, String userName, String nickName) {
    BotAssertTool.isActualTrue(userId == null, BotExceptionType.BOT_SERVICE_PARAMS, "用户ID不能为空");
    BotAssertTool.isActualTrue(pLatForm == null, BotExceptionType.BOT_SERVICE_PARAMS, "bot平台不能为空");
    BotBind botBind =
        botBindRepository.findByPlatFormAndPlatFormId(pLatForm, userId).orElse(null);

    if (botBind != null) {
      return botBind;
    }
    BotUser botUser = new BotUser();
    botUser.setName(userName);
    botUser.setNickName(nickName);
    botUser = botUserRepository.save(botUser);
    //保存绑定信息
    botBind = new BotBind();
    botBind.setId(0);
    botBind.setPlatForm(pLatForm);
    botBind.setPlatFormId(userId.toString());
    botBind.setBotUser(botUser);
    return botBindRepository.save(botBind);
  }


  public BotBind getByPlatformAndPlatformId(String platform, String platformId) {
    return botBindRepository.findByPlatFormAndPlatFormId(platform, platformId).orElse(null);
  }
}
