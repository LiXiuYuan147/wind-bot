package woaini.fenger.bot.core.command.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.bind.domain.BotRole;
import woaini.fenger.bot.core.bind.domain.BotUser;
import woaini.fenger.bot.core.bind.respository.BotRoleRepository;
import woaini.fenger.bot.core.bind.respository.BotUserRepository;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.CmdParams;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.command.utils.CmdTool;
import woaini.fenger.bot.core.dispatcher.impl.CmdInterceptor;
import woaini.fenger.bot.core.exception.BotAssertTool;
import woaini.fenger.bot.core.exception.enums.BotExceptionType;
import woaini.fenger.bot.core.session.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 角色授权相关
 *
 * @see woaini.fenger.bot.core.command.system.RoleAuthCmd
 * @author yefeng {@date 2024-10-14 14:59:32}
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RoleAuthCmd implements ICmd {

  private final BotRoleRepository botRoleRepository;
  private final BotUserRepository botUserRepository;

  @Override
  public String masterCmdName() {
    return "role";
  }

  @Override
  public String description() {
    return "角色授权相关命令";
  }

  @Override
  public boolean auth() {
    return true;
  }

  /**
   * 查看所有角色
   *
   * @param session 上下文
   * @author yefeng {@date 2024-10-14 15:10:51}
   * @since 1.0
   */
  @SubCmd(value = "list",auth = true)
  public void list(Session session, @CmdParams(value = "@1", description = "指定用户") Integer userId) {

    StringBuilder stringBuilder = new StringBuilder();
    List<BotRole> roles = new ArrayList<>(0);
    if (userId != null) {
      // 获取用户信息
      Optional<BotUser> optionalBotUser = botUserRepository.findById(userId);
      BotAssertTool.isActualTrue(
          optionalBotUser.isEmpty(), BotExceptionType.BOT_SERVICE_PARAMS, "用户不存在!");
      roles = optionalBotUser.get().getRoles();
    } else {
      roles = botRoleRepository.findAll();
    }

    stringBuilder.append("名称").append(":").append("描述").append("\r\n");

    for (BotRole role : roles) {
      stringBuilder
          .append(role.getRoleName())
          .append(":")
          .append(role.getDescription())
          .append("\r\n");
    }
    session.replyMessage(stringBuilder.toString());
  }

  @SubCmd(value = "add", description = "添加角色",auth = true)
  public void add(
      Session session,
      @CmdParams(value = "[0]", description = "名字", required = true) String name,
      @CmdParams(value = "[1]", description = "描述", required = true) String description) {

    log.info("添加角色:{},{}", name, description);
    // 查询角色是否存在
    boolean exists = botRoleRepository.existsByRoleName(name);
    BotAssertTool.isActualTrue(exists, BotExceptionType.BOT_SERVICE_PARAMS, "角色名称已经存在!");

    // 添加角色信息
    BotRole role = new BotRole();
    role.setRoleName(name);
    role.setDescription(description);
    botRoleRepository.save(role);
    session.replyMessage("添加角色成功!");
  }

  @SubCmd(value = "del", description = "删除角色",auth = true)
  public void del(
      Session session,
      @CmdParams(value = "[0]", description = "角色名称", required = true) String name) {

    log.info("删除角色:{}", name);
    // 删除角色信息
    botRoleRepository.deleteByRoleName(name);
    session.replyMessage("删除角色成功!");
  }

  @SubCmd(value = "auth", description = "授权角色",auth = true)
  public void auth(
      Session session,
      @CmdParams(value = "[0]", description = "角色名称", required = true) String name,
      @CmdParams(value = "@1", description = "第一个艾特的用户") Integer userId,
      @CmdParams(value = "R", description = "是否移除") Boolean remove) {

    log.info("角色授权{},{}", name, userId);

    BotRole dbRole = botRoleRepository.findByRoleName(name);
    BotAssertTool.isActualTrue(dbRole == null, BotExceptionType.BOT_SERVICE_PARAMS, "角色不存在!");

    // 获取用户信息
    Optional<BotUser> optionalBotUser = botUserRepository.findById(userId);
    BotAssertTool.isActualTrue(
        optionalBotUser.isEmpty(), BotExceptionType.BOT_SERVICE_PARAMS, "用户不存在!");

    BotUser botUser = optionalBotUser.get();
    if (remove) {
      botUser.getRoles().remove(dbRole);
      session.replyMessage("移除角色成功!");
    }else {
      botUser.getRoles().add(dbRole);
      session.replyMessage("授权角色成功!");
    }
    botUserRepository.save(botUser);
    log.info("角色授权成功{},{}", name, userId);
  }
  @SubCmd(value = "bind", description = "绑定权限",auth = true)
  public void bind(
      Session session,
      @CmdParams(value = "[0]", description = "角色名称", required = true) String name,
      @CmdParams(value = "[1]", description = "命令名称", required = true) String cmdCode,
      @CmdParams(value = "R", description = "是否移除") Boolean remove) {

    log.info("角色绑定权限{},{}", name, cmdCode);
    BotRole dbRole = botRoleRepository.findByRoleName(name);
    BotAssertTool.isActualTrue(dbRole == null, BotExceptionType.BOT_SERVICE_PARAMS, "角色不存在!");

    // 对应的命令
    String masterCmdName = StrUtil.subBefore(cmdCode, ":", true);
    String subCmdName = StrUtil.subAfter(cmdCode, ":", true);

    ICmd cmd = CmdInterceptor.cmdMaps.get(masterCmdName);
    BotAssertTool.isActualTrue(cmd == null, BotExceptionType.BOT_SERVICE_PARAMS, "命令不存在!");

    if (StrUtil.isNotEmpty(subCmdName)) {
      BotAssertTool.isActualTrue(
          !CmdTool.existsSubCmd(cmd, subCmdName),
          BotExceptionType.BOT_SERVICE_PARAMS,
          "子命令:{},不存在!",
          subCmdName);
      //存在子权限的情况下 校验主权限是否已经单独存在
      boolean contains = dbRole.getCmds().contains(masterCmdName);
      if (contains && !remove){
        session.replyMessage("角色已经存在"+masterCmdName+"的权限!");
        return;
      }
    }
    boolean contains = dbRole.getCmds().contains(cmdCode);
    if (remove){
      if (!contains){
        session.replyMessage("角色未绑定权限!");
        return;
      }
      dbRole.getCmds().remove(cmdCode);
    }else {
      if (contains){
        session.replyMessage("角色已经绑定权限!");
        return;
      }
      dbRole.getCmds().add(cmdCode);
    }
    botRoleRepository.save(dbRole);
    session.replyMessage("角色绑定权限成功!");
  }
  @SubCmd(value = "show", description = "查询角色权限列表",auth = true)
  public void show(
    Session session,
    @CmdParams(value = "[0]", description = "角色名称", required = true) String name) {

    BotRole dbRole = botRoleRepository.findByRoleName(name);
    BotAssertTool.isActualTrue(dbRole == null, BotExceptionType.BOT_SERVICE_PARAMS, "角色不存在!");
    if (CollUtil.isEmpty(dbRole.getCmds())){
      session.replyMessage("当前为配置任何权限");
      return;
    }

    session.replyMessage(StrUtil.join("|",dbRole.getCmds()));
  }
}
