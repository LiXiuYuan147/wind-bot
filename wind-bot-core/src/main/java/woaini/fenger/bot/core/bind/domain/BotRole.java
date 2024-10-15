package woaini.fenger.bot.core.bind.domain;

import cn.hutool.core.collection.CollUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import woaini.fenger.bot.core.jpa.convert.StringListConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 机器人角色控制 一个人可以有多个角色 权限为所有角色的权限码集合
 *
 * @see woaini.fenger.bot.core.bind.domain.BotRole
 * @author yefeng {@date 2024-10-14 11:47:22}
 */
@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "bot_role")
public class BotRole implements Serializable {

  /**
   * @see Integer ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * @see String 角色名称
   */
  private String roleName;

  /**
   * @see String 描述
   */
  private String description;

  @Convert(converter = StringListConverter.class)
  private Set<String> cmds;

  public Set<String> getCmds() {
    if (CollUtil.isNotEmpty(cmds)){
      return cmds;
    }
    return new HashSet<>(0);
  }

  @PrePersist
  public void prePersist() {
    if (cmds == null){
      cmds = new HashSet<>(0);
    }
  }
}
