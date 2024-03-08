package woaini.fenger.bot.core.event.action;

import java.util.Map;
import java.util.function.Consumer;
import lombok.experimental.UtilityClass;
import woaini.fenger.bot.core.event.segment.Messages;

/**
 * 动作工具类
 *
 * @see woaini.fenger.bot.core.event.action.ActionTool
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@UtilityClass
public class ActionTool {

  /**
   * @MethodName of
   *
   * @param actionName 操作名称
   * @param consumer 消费者
   * @author yefeng {@date 2024-03-06 12:00:35}
   * @since 1.0
   * @return {@link ActionRequest }
   */
  public ActionRequest of(String actionName, Consumer<Map<String, Object>> consumer) {
    ActionRequest actionRequest = new ActionRequest();
    actionRequest.setAction(actionName);
    consumer.accept(actionRequest.getParams());
    return actionRequest;
  }
}
