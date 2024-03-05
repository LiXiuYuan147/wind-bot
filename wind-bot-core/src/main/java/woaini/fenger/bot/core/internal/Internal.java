package woaini.fenger.bot.core.internal;


import woaini.fenger.bot.core.bot.Bot;

/**
 * 内部 方法适配 实际进行通信在这里进行
 *
 * @see Internal
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public  abstract class Internal {

    public Bot bot;
    public Internal(Bot bot) {
        this.bot = bot;
    }
}
