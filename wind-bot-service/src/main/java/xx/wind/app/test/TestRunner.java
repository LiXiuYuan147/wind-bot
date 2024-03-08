package xx.wind.app.test;
import org.springframework.beans.factory.annotation.Autowired;
import woaini.fenger.bot.core.bot.enums.ConnectType;
import java.util.HashMap;

import org.springframework.stereotype.Component;
import woaini.fenger.bot.code.impl.onebot.OneBot;
import woaini.fenger.bot.code.impl.onebot.config.OneBotConfig;
import woaini.fenger.bot.core.boot.ApplicationStartupCompleted;
import woaini.fenger.bot.core.manager.BotManager;

@Component
public class TestRunner implements ApplicationStartupCompleted {

    @Autowired
    private BotManager botManager;

    @Override
    public void onInit() {

//        OneBotConfig config = new OneBotConfig();
//        config.setParams(new HashMap<String,Object>());
//        config.setHeaders(new HashMap<String,String>());
//        config.setExtra(new HashMap<String,Object>());
//        config.setSelfId("609216758");
//        config.setPlatForm("qq");
//        config.setAgreement("ontBot");
//        config.setReceiveConnectType(ConnectType.REVERSE_WS);
//        config.setSendConnectType(ConnectType.REVERSE_WS);
//        config.setExtra("reverseWsPort",12000);
//        botManager.registerBot(config);
    }
}
