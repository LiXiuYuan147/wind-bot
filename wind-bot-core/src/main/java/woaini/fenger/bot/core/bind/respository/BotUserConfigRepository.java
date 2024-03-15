package woaini.fenger.bot.core.bind.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woaini.fenger.bot.core.bind.domain.BotUserConfig;
import woaini.fenger.bot.core.bind.key.BotUserConfigKey;

/**
 * 机器人用户配置库
 *
 * @see woaini.fenger.bot.core.bind.respository.BotUserConfigRepository
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Repository
public interface BotUserConfigRepository extends JpaRepository<BotUserConfig, BotUserConfigKey> {
  BotUserConfig findByBotUserIdAndConfigKey(String botUserId, String configKey);

  BotUserConfig findByLocalKeyAndConfigKey(String localKey, String configKey);
}
