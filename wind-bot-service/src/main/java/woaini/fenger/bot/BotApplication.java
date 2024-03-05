package woaini.fenger.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * BOT应用
 *
 * @see woaini.fenger.bot.BotApplication
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@SpringBootApplication
@EnableAsync
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class BotApplication {
  public static void main(String[] args) {
    SpringApplication.run(BotApplication.class, args);
  }
}
