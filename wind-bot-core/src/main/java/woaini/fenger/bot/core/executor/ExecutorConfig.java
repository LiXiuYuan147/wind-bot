package woaini.fenger.bot.core.executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

  @Bean
   public ExecutorService getExecutorService(){
     return Executors.newVirtualThreadPerTaskExecutor();
   }

}
