package woaini.fenger.bot.core.event.base;

import com.alibaba.fastjson2.annotation.JSONField;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import woaini.fenger.bot.core.event.enums.EventType;
import woaini.fenger.bot.core.event.segment.Messages;

/**
 * 事件
 *
 * @see Event
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class Event implements Serializable {

  /**
   * @see String 事件唯一标识符
   */
  private String id;

  /**
   * @see Self 机器人自身标识
   */
  private Self self;

  /**
   * @see Long 事件发生时间（Unix 时间戳），单位：秒，建议优先采用聊天平台给出的时间，其次采用实现中创建事件对象的时间
   */
  private Long time;

  /**
   * @see EventType 事件类型，必须是 meta、message、notice、request 中的一个，分别表示元事件、消息事件、通知事件和请求事件
   */
  private EventType type;

  /**
   * @see String 事件详细类型
   */
  @JSONField(name = "detail_type")
  private String detailType;

  /**
   * @see String 事件子类型（详细类型的下一级类型）
   */
  @JSONField(name = "sub_type")
  private String subType;

  /**
   * @see String 消息id
   */
  @JSONField(name = "message_id")
  private String messageId;

  /**
   * @see String 纯文字消息
   */
  @JSONField(name = "alt_message")
  private String altMessage;

  /**
   * @see Map<String, String> 额外扩展字段
   */
  private Map<String, String> extra;

  /**
   * @see Messages 讯息
   */
  private Messages message;

  public Event() {
    extra = new HashMap<>(5);
  }

  /**
   * @MethodName getExtraField
   *
   * @param key key
   * @author yefeng {@date 2024-03-05 10:10:37}
   * @since 1.0
   * @return {@link String }
   */
  public String getExtraField(String key) {
    return extra.get(key);
  }

  /**
   * @MethodName setExtraField
   * @param platForm 平台
   * @param key key
   * @param value 值
   * @author yefeng {@date 2024-03-05 10:10:48}
   * @since 1.0
   */
  public void setExtraField(String platForm,String key, String value) {
    extra.put(key, value);
  }
}
