package woaini.fenger.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import xx.wind.app.BotApplication;

import java.io.UnsupportedEncodingException;

@Slf4j
@Test
@SpringBootTest(classes = BotApplication.class)
public class BotApplicationTest extends AbstractTestNGSpringContextTests {

  @Test
  public void test() {}

  public static void main(String[] args) throws UnsupportedEncodingException {
    String s = "我不知道啊";
    byte[] bytes = s.getBytes("ISO8859-1");
    System.out.println(new String(bytes));
  }
}
