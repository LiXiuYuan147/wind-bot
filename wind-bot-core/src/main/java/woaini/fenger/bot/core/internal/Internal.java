package woaini.fenger.bot.core.internal;

import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.event.action.ActionRequest;
import woaini.fenger.bot.core.event.action.ActionResponse;
import woaini.fenger.bot.core.event.action.ActionTool;
import woaini.fenger.bot.core.event.segment.Messages;

/**
 * 内部 方法适配 实际进行通信在这里进行 这里预设得有基础的方法吧
 *
 * @see Internal
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public class Internal {

  public Bot bot;

  public Internal(Bot bot) {
    this.bot = bot;
  }

  /**
   * @MethodName sendPrivateMessage
   *
   * @param userId 用户ID
   * @param messages 消息
   * @author yefeng {@date 2024-03-08 14:33:28}
   * @since 1.0
   * @return {@link ActionResponse } 发送私信
   */
  public ActionResponse sendPrivateMessage(String userId, Messages messages) {
    ActionRequest actionRequest =
        ActionTool.of(
            "send_message",
            params -> {
              params.put("detail_type", "private");
              params.put("user_id", userId);
              params.put("message", messages);
            });
    return bot.action(actionRequest);
  }

    /**
     * @MethodName sendGroupMessage
     * @param groupId 组ID
     * @param messages 消息
     * @author yefeng
     * {@date 2024-03-08 14:37:28}
     * @since 1.0
     * @return {@link ActionResponse }
     * 发送群发消息
     */
    public ActionResponse sendGroupMessage(String groupId, Messages messages) {
        ActionRequest actionRequest =
                ActionTool.of(
                        "send_message",
                        params -> {
                            params.put("detail_type", "group");
                            params.put("group_id", groupId);
                            params.put("message", messages);
                        });
        return bot.action(actionRequest);
    }
}
