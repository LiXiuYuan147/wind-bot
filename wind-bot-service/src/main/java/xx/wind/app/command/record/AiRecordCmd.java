package xx.wind.app.command.record;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.CmdParams;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.event.segment.Messages;
import woaini.fenger.bot.core.session.Session;


/**
 * 记录在案
 *
 * @see AiRecordCmd
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Component
public class AiRecordCmd implements ICmd {

  private static final String endpoint = "https://www.xn--wxtz62e.site";

  private static final String MODEL_LIST_URL = "/models";
  private static final String RUN_LIST_URL = "/run";

  @Override
  public String masterCmdName() {
    return "语音合成";
  }

  @Override
  public String description() {
    return "合成训练的语音，可能会出错";
  }

  @SubCmd
  public void run(
      Session session,
      @CmdParams(value = "[0]", description = "合成的语音内容", required = true) String content,
      @CmdParams(value = "s", description = "合成人的名称", required = true) Integer speaker,
      @CmdParams(value = "l", description = "合成语言长度",  defaultValue = "1.1")
          Double length,
      @CmdParams(value = "n", description = "情感起伏大小", defaultValue = "0.2")
          Double noise,
      @CmdParams(value = "noisew", description = "音素发音成都", defaultValue = "0.37")
          Double noisew) {

    Map<String, Object> params = new HashMap<>(5);
    params.put("id_speaker", speaker);
    params.put("text", content);
    params.put("length", length);
    params.put("noise", noise);
    params.put("noisew", noisew);

    String url = endpoint + RUN_LIST_URL + "?" + HttpUtil.toParams(params);
//    session.replyMessage(Messages.builder().record(url));
  }

  @SubCmd(value = "模型列表", description = "获取模型列表")
  public void modelList(Session session) {
    String body = HttpRequest.get(endpoint + MODEL_LIST_URL).execute().body();
    if (StrUtil.isNotBlank(body)) {
      List<String> models =
          JSONObject.parseObject(
              body,
              new TypeReference<List<String>>() {
                @Override
                public List<String> parseObject(String text) {
                  return super.parseObject(text);
                }
              });

      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < models.size(); i++) {
        stringBuilder.append(models.get(i)).append("\t");
        if (i % 2 == 0) {
          stringBuilder.append("\r\n");
        }
      }
      Messages messages = Messages.builder().text(stringBuilder.toString());
      session.replyMessage(messages);
    }
  }
}
