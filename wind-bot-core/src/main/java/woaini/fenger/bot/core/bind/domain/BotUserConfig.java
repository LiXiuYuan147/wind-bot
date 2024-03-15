package woaini.fenger.bot.core.bind.domain;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import woaini.fenger.bot.core.bind.key.BotUserConfigKey;

/**
 * 机器人用户配置
 *
 * @see import woaini.fenger.core.bind.key.BotUserConfigKey;.BotUserConfig
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "bot_user_config")
@IdClass(BotUserConfigKey.class)
public class BotUserConfig implements Serializable {

  /**
   * @see String 机器人用户ID
   */
  @Id private String botUserId;

  /**
   * @see String 配置键 例如 qylc 是公司相关配置 xiaomirun 是跑步相关配置 如果存在多重配置 用.进行分割比如 qylc.clock
   */
  @Id private String configKey;

  /**
   * @see String 本地查询关键 比如手机号啥的
   */
  private String localKey;

  /**
   * @see String 配置值 通常是json格式的字符串
   */
  @Column(columnDefinition = "json")
  private String configValue;

  /**
   * @see String 描述
   */
  private String description;

  @Transient
  public <R> R getValue(Class<R> rClass) {
    if (StrUtil.isNotEmpty(configValue)) {
      return JSONObject.parseObject(configValue, rClass);
    }
    return null;
  }
}
