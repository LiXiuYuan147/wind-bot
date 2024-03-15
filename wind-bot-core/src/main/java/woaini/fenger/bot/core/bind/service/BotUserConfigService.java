package woaini.fenger.bot.core.bind.service;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import woaini.fenger.bot.core.bind.domain.BotUserConfig;
import woaini.fenger.bot.core.bind.respository.BotUserConfigRepository;

/**
 * BOT用户配置服务
 *
 * @see woaini.fenger.bot.core.bind.service.BotUserConfigService
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Service
@AllArgsConstructor
public class BotUserConfigService {

  private BotUserConfigRepository botUserConfigRepository;

  /**
   * @MethodName getBotUserConfig
   *
   * @param botUserId 机器人用户ID
   * @param configKey 配置键
   * @param rClass R级
   * @author yefeng {@date 2024-01-29 17:21:18}
   * @since 1.0
   * @return {@link R } 获取机器人用户配置
   */
  public <R> R getBotUserConfig(String botUserId, String configKey, Class<R> rClass) {
    BotUserConfig dbBotUserConfig =
        botUserConfigRepository.findByBotUserIdAndConfigKey(botUserId, configKey);
    if (dbBotUserConfig != null) {
      return dbBotUserConfig.getValue(rClass);
    }
    return null;
  }

  public <R> R getBotUserLocalConfig(String configKey, String localKey, Class<R> rClass) {
    BotUserConfig dbBotUserConfig =
            botUserConfigRepository.findByLocalKeyAndConfigKey(localKey, configKey);
    if (dbBotUserConfig != null) {
      return dbBotUserConfig.getValue(rClass);
    }
    return null;
  }

  /**
   * @MethodName saveBotUserConfig
   *
   * @param botUserId 机器人用户ID
   * @param configKey 配置键
   * @param object 对象
   * @author yefeng {@date 2024-01-29 17:21:13}
   * @since 1.0
   *     <p>保存机器人用户配置
   */
  public void saveBotUserConfig(String botUserId, String configKey, Object object) {
    BotUserConfig dbBotUserConfig =
        botUserConfigRepository.findByBotUserIdAndConfigKey(botUserId, configKey);
    if (dbBotUserConfig == null) {
      dbBotUserConfig = new BotUserConfig();
      dbBotUserConfig.setBotUserId(botUserId);
      dbBotUserConfig.setConfigKey(configKey);
    }
    dbBotUserConfig.setConfigValue(JSONObject.toJSONString(object));
    botUserConfigRepository.save(dbBotUserConfig);
  }
}
