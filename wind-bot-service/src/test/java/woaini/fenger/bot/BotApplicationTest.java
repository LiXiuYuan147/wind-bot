package woaini.fenger.bot;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSONObject;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.entity.images.Generations;
import com.plexpt.chatgpt.entity.images.ImagesRensponse;
import com.plexpt.chatgpt.entity.images.Variations;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import xx.wind.app.BotApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Test
@SpringBootTest(classes = BotApplication.class)
public class BotApplicationTest extends AbstractTestNGSpringContextTests {

  @Test
  public void test() {}

  static {
    System.load("/home/xiuyuan/下载/opencv-4.9.0/build/lib/libopencv_java490.so");
  }

  public static void main(String[] args) {


    ChatGPT chatGPT = ChatGPT.builder()
      .apiKey("sk-AFNPPcSe9utAy3rtI5eflTftn24I1GKDXtuh4r4cEVxnmvkY")
      .apiHost("https://api.chatanywhere.tech/")
      .build()
      .init();

    File file = FileUtil.newFile("/home/xiuyuan/图片/right.jpg");

    Variations variations = Variations.ofB64_JSON(1, "256x256");

    Generations generations = Generations.builder()
      .prompt("一只可爱的小猫咪")
      .model("dall-e-2")
      .n(1)
      .size("256x256")
      .build();

    ImagesRensponse imagesRensponse = chatGPT.imageGeneration(generations);
    System.out.println(JSONObject.toJSONString(imagesRensponse));

//    Message system = Message.ofSystem("你现在是一个诗人，专门写七言绝句");
//    Message message = Message.of("写一段七言绝句诗，题目是：火锅！");
//
//    ChatCompletion chatCompletion = ChatCompletion.builder()
//      .model("gpt-3.5-turbo-0125")
//      .messages(Arrays.asList(system, message))
//      .build();
//
//    ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
//    System.out.println(response.toPlainString());
  }
}
