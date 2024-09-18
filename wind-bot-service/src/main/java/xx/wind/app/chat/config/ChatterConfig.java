package xx.wind.app.chat.config;

import com.plexpt.chatgpt.entity.chat.Message;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatterConfig {

  private String host;

  private Integer port;

  private String apiKey;

  private String model;

  private List<Message> prompts;


  /**
   * @see Boolean 是否开启记忆功能
   */
  protected Boolean memory = false;
}
