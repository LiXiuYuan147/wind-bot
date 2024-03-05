package woaini.fenger.bot.core.boot;

/**
 * 应用程序启动已完成 应用启动完成后自动执行的接口
 *
 * @see ApplicationStartupCompleted
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public interface ApplicationStartupCompleted {

  /**
   * @MethodName onInit
   *
   * @author yefeng {@date 2024-01-19 15:47:10}
   * @since 1.0
   *     <p>在初始化时 执行的接口
   */
  void onInit();

  /**
   * @MethodName order
   *
   * @author yefeng {@date 2024-01-19 15:44:40}
   * @since 1.0
   * @return int 启动顺序
   */
  default int order() {
    return 99;
  }
}
