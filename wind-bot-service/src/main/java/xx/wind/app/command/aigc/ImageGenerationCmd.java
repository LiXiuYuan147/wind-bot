package xx.wind.app.command.aigc;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.images.Generations;
import com.plexpt.chatgpt.entity.images.ImagesRensponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.CmdParams;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.event.segment.Messages;
import woaini.fenger.bot.core.session.Session;

import java.util.concurrent.ExecutorService;


/**
 * 图片生成用
 * @see  xx.wind.app.command.aigc.ImageGenerationCmd
 * @author yefeng
 * {@date 2024-09-20 18:10:48}
 */
@Component
@RequiredArgsConstructor
public class ImageGenerationCmd implements ICmd {

  private ChatGPT chatGPT;

  private final ExecutorService executorService;

  @Override
  public String masterCmdName() {
    return "图像生成";
  }

  @Override
  public String description() {
    return "ai生成图像";
  }

  @SubCmd
  public void execute(Session session,
                      @CmdParams(value = "p", description = "提示词") String prompt) {
    if (chatGPT == null){
      this.init();
    }
    Generations generations = Generations.builder()
      .prompt(prompt)
      .model("dall-e-2")
      .n(1)
      .size("256x256")
      .build();
    session.replyMessage("生成中>>>>>>滴滴滴滴>>>>>");
    executorService.execute(() -> {
      ImagesRensponse imagesRensponse = chatGPT.imageGeneration(generations);
      session.replyMessage("生成完毕,构建返回信息");
      String fileUrl = imagesRensponse.getUrls().get(0);
      Messages messages = Messages
        .builder()
        .image(fileUrl);
      session.replyMessage(messages);
    });
  }

  private void  init (){
    chatGPT = ChatGPT.builder()
      .apiKey("sk-AFNPPcSe9utAy3rtI5eflTftn24I1GKDXtuh4r4cEVxnmvkY")
      .apiHost("https://api.chatanywhere.tech/")
      .build()
      .init();
  }
}
