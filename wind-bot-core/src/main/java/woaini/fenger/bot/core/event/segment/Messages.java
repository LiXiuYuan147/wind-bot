package woaini.fenger.bot.core.event.segment;

import cn.hutool.core.util.StrUtil;
import woaini.fenger.bot.core.event.segment.impl.Mention;
import woaini.fenger.bot.core.event.segment.impl.MentionAll;
import woaini.fenger.bot.core.event.segment.impl.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息
 *
 * @see woaini.fenger.bot.core.event.segment.Messages
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public class Messages {

  /**
   * @see List <Segment> 元素
   */
  private List<Segment> elements;

  public Messages() {
    this.elements = new ArrayList<>();
  }

  public static Messages builder() {
    return new Messages();
  }

  public Messages add(Segment segment) {
    this.elements.add(segment);
    return this;
  }

  public Messages at(String id) {
    return this.add(new Mention(id));
  }

  public Messages atAll() {
    return this.add(new MentionAll());
  }

  public Messages text(String text) {
    return this.add(new Text(text));
  }

  public Messages text(String text,Object ...args) {
    String formatted = StrUtil.format(text, args);
    return this.add(new Text(formatted));
  }

}
