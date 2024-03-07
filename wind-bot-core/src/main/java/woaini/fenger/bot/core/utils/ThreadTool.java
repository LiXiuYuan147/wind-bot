package woaini.fenger.bot.core.utils;

import com.jd.platform.async.callback.IWorker;
import com.jd.platform.async.executor.Async;
import com.jd.platform.async.wrapper.WorkerWrapper;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 线程工具类
 *
 * @see woaini.fenger.bot.core.utils.ThreadTool
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@UtilityClass
@Slf4j
public class ThreadTool {

  private static final ThreadFactory FACTORY =
      Thread.ofVirtual()
          .name("virtual-thread-bot-2-", 1)
          .uncaughtExceptionHandler(
              new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                  log.error("虚拟线程Id:{}, 虚拟线程名:{}, 依附的平台线程:{}", t.threadId(), t.getName(), t, e);
                }
              })
          .factory();
  private static final ExecutorService executorService =
      Executors.newThreadPerTaskExecutor(FACTORY);

  /**
   * @MethodName start
   *
   * @param runnable 可运行
   * @param timeOut 超时时间 单位秒
   * @author yefeng {@date 2024-03-07 09:55:14}
   * @since 1.0 提交任务
   */
  public void submitTask(Runnable runnable, long timeOut) {

      BotWorker botWorker = new BotWorker();

      WorkerWrapper<Runnable, Void> workerWrapper =
              new WorkerWrapper.Builder<Runnable, Void>()
                      .worker(botWorker)
                      .param(runnable)
                      .build();
      try{
          Async.beginWork(timeOut * 1000, executorService, workerWrapper);
      }catch (Exception ex){
          log.error("任务提交失败", ex);
      }
  }

    public void run(Runnable runnable) {
        executorService.submit(runnable);
    }

  public static class BotWorker implements IWorker<Runnable, Void> {

    @Override
    public Void action(Runnable object, Map<String, WorkerWrapper> allWrappers) {
      object.run();
      return null;
    }

    @Override
    public Void defaultValue() {
      return null;
    }
  }
}
