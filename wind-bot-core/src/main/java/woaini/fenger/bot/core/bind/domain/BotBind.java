package woaini.fenger.bot.core.bind.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * 平台绑定关系
 *
 * @see BotBind
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Getter
@Setter
@Entity
@FieldNameConstants
@Table(
    name = "bot_bind",
    indexes = {@Index(name = "idx_botbind_platform", columnList = "platForm, platFormId")})
public class BotBind implements Serializable {

  /**
   * @see Integer ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * @see String 平台标识
   */
  private String platForm;

  /**
   * @see String 平台ID 例如qq就是qq号
   */
  private String platFormId;

  /**
   * @see BotUser 对应的唯一用户
   */
  @ManyToOne private BotUser botUser;
}
