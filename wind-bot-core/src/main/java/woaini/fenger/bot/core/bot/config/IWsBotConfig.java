package woaini.fenger.bot.core.bot.config;

/**
 * WS机器人配置 需要的配置
 *
 * @see woaini.fenger.bot.core.bot.config.IWsBotConfig
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public interface IWsBotConfig {

    /**
     * @MethodName getWsHost
     *
     * @author yefeng
     * {@date 2024-03-05 11:36:06}
     * @since 1.0
     * @return {@link String }
     * 获取%ws主机
     */
    String getWsHost();

    /**
     * @MethodName reconnectionInterval
     *
     * @author yefeng
     * {@date 2024-03-05 11:36:03}
     * @since 1.0
     * @return int
     * 重新连接间隔
     */
    int reconnectionInterval();

}
