package woaini.fenger.bot.code.impl.onebot.auto;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.code.impl.onebot.OneBot;
import woaini.fenger.bot.code.impl.onebot.config.OneBotAutoConfig;
import woaini.fenger.bot.code.impl.onebot.config.OneBotConfig;
import woaini.fenger.bot.core.boot.IBotAutoRegister;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.manager.BotManager;

/**
 * OneBot自动注册
 *
 * @see woaini.fenger.bot.code.impl.onebot.auto.OneBotAutoRegister
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
@EnableConfigurationProperties(OneBotAutoConfig.class)
@AllArgsConstructor
public class OneBotAutoRegister implements IBotAutoRegister {

  private final OneBotAutoConfig oneBotAutoConfig;

  @Override
  public String name() {
    return OneBot.NAME;
  }

  @Override
  public List<Bot> autoRegister() {
    List<Bot> botList = new ArrayList<>();
    // 遍历配置 进行读取所有的
    List<OneBotConfig> configs = oneBotAutoConfig.getBots();
    if (CollUtil.isEmpty(configs)){
      return botList;
    }
    for (OneBotConfig config : configs) {
      OneBot oneBot = new OneBot(config);
      botList.add(oneBot);
    }
    return botList;
  }
}
