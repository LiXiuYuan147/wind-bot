package woaini.fenger.bot;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import xx.wind.app.BotApplication;

import java.util.ArrayList;
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

    TimeInterval timer = DateUtil.timer();
    timer.start();
    Mat src = Imgcodecs.imread("/home/xiuyuan/图片/opencv/1.png"); // 待匹配图片
    Mat template = Imgcodecs.imread("/home/xiuyuan/图片/opencv/主角.png"); // 获取匹配模板

    // 确保图片被正确加载
    if (src.empty() || template.empty()) {
      System.out.println("Error loading images");
      return;
    }

    // 创建结果矩阵
    int result_cols = src.cols() - template.cols() + 1;
    int result_rows = src.rows() - template.rows() + 1;
    Mat result = new Mat(result_rows, result_cols, CvType.CV_32F);

    // 进行模板匹配
    Imgproc.matchTemplate(src, template, result, Imgproc.TM_CCOEFF_NORMED);

    // 设置匹配阈值
    double threshold = 0.8;

    Core.MinMaxLocResult minMaxLocResult = Core.minMaxLoc(result);

    // 通过阈值匹配结果
    if (minMaxLocResult.maxVal > threshold) {
      // 获取匹配结果
      Point matchLoc = minMaxLocResult.maxLoc;
      // 原来的图片上画矩形
      Imgproc.rectangle(
          src,
          matchLoc,
          new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows()),
          new Scalar(0, 255, 0),
          2);
    }
    HighGui.imshow("Match Result", src);
    HighGui.waitKey();

    //    System.out.println(timer.interval());
    //    // 绘制矩形
    //    for (Mat loc : matchLoc) {
    //      Imgproc.rectangle(
    //          img,
    //          new Point(loc.get(0, 0)[0], loc.get(0, 0)[1]),
    //          new Point(loc.get(0, 0)[0] + template.cols(), loc.get(0, 0)[1] + template.rows()),
    //          new Scalar(0, 255, 0),
    //          2);
    //      HighGui.imshow("Match Result", img);
    //      int waitKey = HighGui.waitKey(0);
    //      if (waitKey != -1) {
    //        HighGui.destroyWindow("Match Result");
    //      }
    //    }
    //
    //    // 释放资源
    //    img.release();
    //    template.release();
    //    result.release();

  }

  public static void canny() {
    // 读取图像
    Mat src = Imgcodecs.imread("/home/xiuyuan/图片/opencv/2.jpg");

    // 转换为灰度图像
    Mat gray = new Mat();
    Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

    // 应用Canny边缘检测
    Mat edges = new Mat();
    Imgproc.Canny(gray, edges, 50, 150);

    // 查找轮廓
    List<MatOfPoint> matOfPoints = new ArrayList<>();
    Imgproc.findContours(
        edges, matOfPoints, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

    for (MatOfPoint matOfPoint : matOfPoints) {
      Rect rect = Imgproc.boundingRect(matOfPoint);
      // 校测矩形大小
      if (rect.width < 100 || rect.height < 100) {
        continue;
      }
      Imgproc.rectangle(src, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);
    }
    HighGui.imshow("Detected Rectangles", src);
    HighGui.waitKey(0);
    HighGui.destroyAllWindows();
  }
}
