package woaini.fenger.bot.core.bind.key;

import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.Data;

/**
 * 机器人用户配置密钥
 * @see  woaini.fenger.bot.core.bind.key.BotUserConfigKey
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */@Data
public class BotUserConfigKey implements Serializable {
    /**
     * @see String 机器人用户ID
     */
    @Id
    private String botUserId;

    /**
     * @see String 配置键 例如 qylc 是公司相关配置 xiaomirun 是跑步相关配置 如果存在多重配置 用.进行分割比如 qylc.clock
     */
    @Id private String configKey;
}
