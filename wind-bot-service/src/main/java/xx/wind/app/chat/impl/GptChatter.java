package xx.wind.app.chat.impl;

import com.plexpt.chatgpt.entity.chat.Message;
import xx.wind.app.chat.AbstractChatter;
import xx.wind.app.chat.config.ChatterConfig;

import java.util.List;

/**
 * @see xx.wind.app.chat.impl.GptChatter
 * @author yefeng {@date 2024-09-03 18:12:34}
 */
public class GptChatter extends AbstractChatter {


  public GptChatter(ChatterConfig chatterConfig) {
    super(chatterConfig);
  }

  @Override
  public String handlerChat(String userId, List<Message> messages) {
    return "";
  }

}
