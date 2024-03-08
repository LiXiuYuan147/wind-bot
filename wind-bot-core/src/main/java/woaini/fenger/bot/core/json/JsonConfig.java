package woaini.fenger.bot.core.json;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.event.segment.Messages;

/**
 * JSON配置
 *
 * @see woaini.fenger.bot.core.json.JsonConfig
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
public class JsonConfig {

  @PostConstruct
  public void init() {

    JSON.register(Messages.class, new Messages.Deserializer());
    JSON.register(Messages.class, new Messages.Serializer());
  }
}
