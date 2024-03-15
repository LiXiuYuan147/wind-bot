package xx.wind.app.botConfig.xiaomi;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * 小米体育配置
 *
 * @see woaini.fenger.botConfig.xiaomi.XiaoMiSportsConfig
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
@FieldNameConstants
public class XiaoMiSportsConfig implements Serializable {

  /**
   * @see String 配置键
   */
  public static final String CONFIG_KEY = "xiaomi.app";

  /**
   * @see String 登录名
   */
  private String loginName;

  /**
   * @see String 密码
   */
  private String password;
}
