package xx.wind.app.command.hitokoto.api;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 伊托科托水库
 *
 * @see woaini.fenger.command.hitokoto.api.HitokotoResDTO
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@NoArgsConstructor
@Data
public class HitokotoResDTO implements Serializable {

  /**
   * @see Integer ID
   */
  private Integer id;

  /**
   * @see String UUID
   */
  private String uuid;

  /**
   * @see String 海托托
   */
  private String hitokoto;

  /**
   * @see String 类型
   */
  private String type;

  /**
   * @see String 从
   */
  private String from;

  /**
   * @see String 发自何人
   */
  private String from_who;

  /**
   * @see String 创造者
   */
  private String creator;

  /**
   * @see Integer 创建者UID
   */
  private Integer creator_uid;

  /**
   * @see Integer 审稿人
   */
  private Integer reviewer;

  /**
   * @see String 提交自
   */
  private String commit_from;

  /**
   * @see String 创建于
   */
  private String created_at;

  /**
   * @see Integer 长度
   */
  private Integer length;
}
