package woaini.fenger.bot.code.impl.onebot.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import woaini.fenger.bot.code.impl.onebot.OneBot;
import woaini.fenger.bot.core.bot.config.BotConfig;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OneBotConfig extends BotConfig {

    public OneBotConfig() {
        this.setAgreement(OneBot.NAME);
    }
}
