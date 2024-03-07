package woaini.fenger.bot.code.impl.onebot;

import woaini.fenger.bot.code.impl.onebot.adapter.OneBotReverseWsAdapter;
import woaini.fenger.bot.code.impl.onebot.config.OneBotConfig;
import woaini.fenger.bot.core.adapter.Adapter;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.bot.enums.ConnectType;
import woaini.fenger.bot.core.internal.Internal;

/**
 * onebot12标准机器人
 * @see  woaini.fenger.bot.code.impl.onebot.OneBot
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */
public class OneBot extends Bot<OneBotConfig> {

    private Adapter sendAdapter;

    private Adapter receiveAdapter;


    public OneBot(OneBotConfig config) {
        super(config);
        init();
    }

    @Override
    public void close() {
        sendAdapter.close();
        receiveAdapter.close();
    }

    public void init(){
        OneBotConfig config = this.getConfig();
        if (config.getSendConnectType().equals(config.getReceiveConnectType())){
            Adapter adapter = getAdapter(config.getSendConnectType());
            this.sendAdapter = adapter;
            this.receiveAdapter = adapter;
        }else {
            this.sendAdapter = getAdapter(config.getSendConnectType());
            this.receiveAdapter = getAdapter(config.getReceiveConnectType());
        }
    }

    private Adapter getAdapter(ConnectType connectType){
        switch (connectType){
            case REVERSE_WS:
                return new OneBotReverseWsAdapter(this);
            default:
                return null;
        }
    }

    @Override
    public Adapter receiveAdapter() {
        return receiveAdapter;
    }

    @Override
    public Adapter sendAdapter() {
        return sendAdapter;
    }

    @Override
    public Internal internal() {
        return null;
    }
}
