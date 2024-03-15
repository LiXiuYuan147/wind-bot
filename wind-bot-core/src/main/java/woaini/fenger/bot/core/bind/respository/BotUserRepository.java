package woaini.fenger.bot.core.bind.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woaini.fenger.bot.core.bind.domain.BotUser;

/**
 * 机器人用户存储库
 *
 * @see woaini.fenger.bot.core.bind.respository.BotUserRepository
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Integer> {}
