package woaini.fenger.bot.core.bind.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * 机器人用户
 * @see  woaini.fenger.bot.core.bind.domain.BotUser
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "bot_user", indexes = {
        @Index(name = "idx_botuser_name", columnList = "name")
})
public class BotUser implements Serializable {

  /**
   * @see Integer ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * @see String 用户名 针对平台的用户名
   */
  private String name;

  /**
   * @see String 昵称
   */
  private String nickName;
}
