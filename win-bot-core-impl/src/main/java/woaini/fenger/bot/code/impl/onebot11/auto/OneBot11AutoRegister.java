package woaini.fenger.bot.code.impl.onebot11.auto;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.code.impl.onebot11.OneBot11;
import woaini.fenger.bot.code.impl.onebot11.config.OneBot11AutoConfig;
import woaini.fenger.bot.code.impl.onebot11.config.OneBot11Config;
import woaini.fenger.bot.core.boot.IBotAutoRegister;
import woaini.fenger.bot.core.bot.Bot;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个bot 11自动注册
 *
 * @see woaini.fenger.bot.code.impl.onebot11.auto.OneBot11AutoRegister
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
@EnableConfigurationProperties(OneBot11AutoConfig.class)
@AllArgsConstructor
public class OneBot11AutoRegister implements IBotAutoRegister {

  private final OneBot11AutoConfig oneBot11AutoConfig;

  @Override
  public String name() {
    return OneBot11.NAME;
  }

  @Override
  public List<Bot> autoRegister() {
    List<Bot> botList = new ArrayList<>();
    List<OneBot11Config> configs = oneBot11AutoConfig.getBots();
    if (CollUtil.isEmpty(configs)){
      return botList;
    }
    for (OneBot11Config config : configs) {
      OneBot11 oneBot11 = new OneBot11(config);
      botList.add(oneBot11);
    }
    return botList;
  }
}
