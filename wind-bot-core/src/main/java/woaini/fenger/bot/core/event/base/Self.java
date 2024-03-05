package woaini.fenger.bot.core.event.base;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 机器人自身信息
 *
 * @see Self
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class Self {

  /**
   * @see String 站台
   */
  private String platform;

  /**
   * @see String 用户id
   */
  @JSONField(name = "user_id")
  private String userId;
}
