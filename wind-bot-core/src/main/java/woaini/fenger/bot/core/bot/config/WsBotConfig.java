package woaini.fenger.bot.core.bot.config;

import lombok.Data;

/**
 * WS机器人配置 需要的配置
 *
 * @see WsBotConfig
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class WsBotConfig extends BotConfig{

    /**
     * @MethodName getWsHost
     *
     * @author yefeng
     * {@date 2024-03-05 11:36:06}
     * @since 1.0
     * @return {@link String }
     * 获取%ws主机
     */
    private String wsHost;
    /**
     * @MethodName reconnectionInterval
     *
     * @author yefeng
     * {@date 2024-03-05 11:36:03}
     * @since 1.0
     * @return int
     * 重新连接间隔 单位 秒
     */
    private Integer reconnectionInterval;
}
