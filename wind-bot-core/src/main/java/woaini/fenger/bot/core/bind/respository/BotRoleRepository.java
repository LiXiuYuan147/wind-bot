package woaini.fenger.bot.core.bind.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woaini.fenger.bot.core.bind.domain.BotRole;

/**
 * @see woaini.fenger.bot.core.bind.respository.BotRoleRepository
 * @author yefeng {@date 2024-10-14 11:51:44}
 */
@Repository
public interface BotRoleRepository extends JpaRepository<BotRole, Integer> {

  boolean existsByRoleName(String roleName);

  void deleteByRoleName(String roleName);

  BotRole findByRoleName(String roleName);


}
