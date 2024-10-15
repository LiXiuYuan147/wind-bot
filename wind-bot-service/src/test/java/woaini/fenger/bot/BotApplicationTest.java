package woaini.fenger.bot;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
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

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.time.LocalDate;
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

  public static void main(String[] args){

  }

  public static Mat bufferedImageToMat(BufferedImage bi) {
    Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
    byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
    mat.put(0, 0, data);
    return mat;
  }

}
