package woaini.fenger.bot.code.impl.onebot11.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import woaini.fenger.bot.code.impl.onebot11.OneBot11;
import woaini.fenger.bot.core.bot.config.BotConfig;

/**
 * 一个bot 11配置
 *
 * @see woaini.fenger.bot.code.impl.onebot11.config.OneBot11Config
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OneBot11Config extends BotConfig {

    public OneBot11Config() {
        super();
        this.setAgreement(OneBot11.NAME);
    }
}
