package woaini.fenger.bot.core.event.action;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import cn.hutool.core.lang.UUID;
import lombok.Data;

/**
 * 动作请求
 *
 * @see ActionRequest
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class ActionRequest {

  /**
   * @see String 可以用于唯一标识一个动作请求
   */
  private String echo;

  /**
   * @see String 动作名称，如 send_message
   */
  private String action;

  /**
   * @see Map<String, Object> 动作参数
   */
  private Map<String, Object> params;

  public ActionRequest() {
    this.params = new HashMap<>(5);
    this.echo = UUID.fastUUID().toString();
  }

}
