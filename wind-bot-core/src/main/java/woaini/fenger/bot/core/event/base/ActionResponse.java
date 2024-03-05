package woaini.fenger.bot.core.event.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import woaini.fenger.bot.core.event.enums.ActionResponseStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 动作返回
 *
 * @see woaini.fenger.bot.core.event.base.ActionResponse
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class ActionResponse implements Serializable {

  /**
   * @see ActionResponseStatus 执行状态（成功与否），必须是 ok、failed 中的一个，分别表示执行成功和失败
   */
  private ActionResponseStatus status;

  /**
   * @see Integer 返回码，必须符合本页后面所定义的返回码规则
   */
  private Integer retcode;

  /**
   * @see String 错误信息，当动作执行失败时，建议在此填写人类可读的错误信息，当执行成功时，应为空字符串
   */
  private String message;

  /**
   * @see String 应原样返回动作请求中的 echo 字段值
   */
  private String echo;

  /**
   * @see Map <String, Object> 响应数据
   */
  private Map<String, Object> data;

  public ActionResponse() {
    this.data = new HashMap<>(5);
  }
}
