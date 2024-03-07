package woaini.fenger.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import xx.wind.app.BotApplication;

@Slf4j
@Test
@SpringBootTest(classes = BotApplication.class)
public class BotApplicationTest extends AbstractTestNGSpringContextTests {

    @Test
    public void test(){
    System.out.println(1);
    }
}
