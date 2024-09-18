package xx.wind.app.chat;

import cn.hutool.core.collection.CollUtil;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.Message;
import xx.wind.app.chat.config.ChatterConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * ai聊天顶层抽象
 *
 * @see AbstractChatter
 * @author yefeng {@date 2024-09-03 17:36:35}
 */
public abstract class AbstractChatter {

  protected ChatterConfig chatterConfig;

  protected List<Message> chatHistory;

  public AbstractChatter(ChatterConfig chatterConfig) {
    this.chatterConfig = chatterConfig;
    chatHistory = new ArrayList<>();
  }

  /**
   * @param userId 用户id
   * @param text 聊天的话
   * @author yefeng {@date 2024-09-03 17:48:06}
   * @since 1.0
   * @return {@link String }
   */
  public String chat(String userId, String text) {
    Message message = Message.of(text);
    //读取配置是否缓存
    if (!chatterConfig.getMemory()){
      return handlerChat(userId, CollUtil.newArrayList(message));
    }
    //存入缓存
    chatHistory.add(message);
    return handlerChat(userId, chatHistory);
  }

  /**
   * @param userId 用户id
   * @param messages 历史消息
   * @author yefeng {@date 2024-09-03 18:19:59}
   * @since 1.0
   * @return {@link String }
   */
  public abstract String handlerChat(String userId, List<Message> messages);

  public static void main(String[] args) {
    ChatGPT chatGPT =
        ChatGPT.builder()
            .apiKey("sk-AFNPPcSe9utAy3rtI5eflTftn24I1GKDXtuh4r4cEVxnmvkY")
            .apiHost("https://api.chatanywhere.tech") // 反向代理地址
            .build()
            .init();

    String res = chatGPT.chat("写一段七言绝句诗，题目是：火锅！");
    System.out.println(res);
  }
}
