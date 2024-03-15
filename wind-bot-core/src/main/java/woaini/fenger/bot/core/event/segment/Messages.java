package woaini.fenger.bot.core.event.segment;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.reader.ObjectReader;
import com.alibaba.fastjson2.writer.ObjectWriter;
import woaini.fenger.bot.core.event.segment.impl.Mention;
import woaini.fenger.bot.core.event.segment.impl.MentionAll;
import woaini.fenger.bot.core.event.segment.impl.Text;

import java.lang.reflect.Type;
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

  public List<Segment> getElements() {
    return elements;
  }

  public void setElements(List<Segment> elements) {
    this.elements = elements;
  }

  public Messages add(Segment segment) {
    this.elements.add(segment);
    return this;
  }
  public boolean isAt(String id) {
    return this.elements.stream().anyMatch(segment -> segment instanceof Mention && ((Mention) segment).getData().getUserId().equals(id));
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


  public static class Deserializer implements ObjectReader<Messages> {
    @Override
    public Messages readObject(JSONReader jsonReader, Type fieldType, Object o, long l) {
      if (jsonReader.nextIfNull()) {
        return null;
      }

      Messages messages = new Messages();
      //消息段序列化
      ArrayList<Segment> segments = new ArrayList<>();
      JSONArray parameters = jsonReader.read(JSONArray.class);
      for (int i = 0; i < parameters.size(); i++) {
        JSONObject jsonObject = parameters.getJSONObject(i);
        String typeString = jsonObject.getString("type");
        if ("text".equals(typeString)){
          Text text = jsonObject.toJavaObject(Text.class);
          segments.add(text);
        }
      }
      messages.setElements(segments);
      return messages;
    }
  }

  public static class Serializer implements ObjectWriter<Messages> {
    @Override
    public void write(JSONWriter jsonWriter, Object o, Object o1, Type type, long l) {
      Messages messages = (Messages) o;
      jsonWriter.write(messages.getElements());
    }
  }
}
