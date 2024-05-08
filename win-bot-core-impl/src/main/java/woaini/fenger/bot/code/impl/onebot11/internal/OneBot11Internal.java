package woaini.fenger.bot.code.impl.onebot11.internal;

import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.event.action.ActionRequest;
import woaini.fenger.bot.core.event.action.ActionResponse;
import woaini.fenger.bot.core.event.action.ActionTool;
import woaini.fenger.bot.core.event.segment.Messages;
import woaini.fenger.bot.core.internal.Internal;

/**
 * 一个bot 11内部
 *
 * @see woaini.fenger.bot.code.impl.onebot11.internal.OneBot11Internal
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public class OneBot11Internal extends Internal {

  public OneBot11Internal(Bot bot) {
    super(bot);
  }

  @Override
  public ActionResponse sendPrivateMessage(String userId, Messages messages) {
    ActionRequest actionRequest =
            ActionTool.of(
                    "send_private_msg",
                    params -> {
                      params.put("user_id", userId);
                      params.put("message", messages);
                    });
    return bot.action(actionRequest);
  }

  @Override
  public ActionResponse sendGroupMessage(String groupId, Messages messages) {
    ActionRequest actionRequest =
            ActionTool.of(
                    "send_group_msg",
                    params -> {
                      params.put("group_id", groupId);
                      params.put("message", messages);
                    });
    return bot.action(actionRequest);
  }
}
