package woaini.fenger.bot.core.bind.respository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woaini.fenger.bot.core.bind.domain.BotBind;

/**
 * 机器人绑定存储库
 *
 * @see woaini.fenger.bot.core.bind.respository.BotBindRepository
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Repository
public interface BotBindRepository extends JpaRepository<BotBind, Integer> {
  /**
   * @MethodName findByPLatFormAndPlatFormId
   *
   * @param platForm 后一种形式
   * @param platFormId 平台ID
   * @author yefeng {@date 2024-01-29 16:00:24}
   * @since 1.0
   * @return {@link Optional }<{@link BotBind }> 按平台和平台ID查找
   */
  Optional<BotBind> findByPlatFormAndPlatFormId(String platForm, String platFormId);
}
