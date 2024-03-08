package woaini.fenger.bot.core.dispatcher;


import woaini.fenger.bot.core.session.Session;

/**
 * IBOTS拦截器
 *
 * @see IBotInterceptor
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public interface IBotInterceptor {

  /**
   * @MethodName dispatch
   *
   * @param session 会话
   * @author yefeng {@date 2024-01-19 15:37:09}
   * @since 1.0
   * @return boolean 是否进入当前处理
   */
  default boolean preDispatch(Session session){
    return true;
  };

  /**
   * @MethodName dispatch
   *
   * @param session 会话
   * @author yefeng {@date 2024-01-19 15:37:09}
   * @since 1.0
   * @return boolean 是否继续下一个处理
   */
  default boolean dispatch(Session session){
    return true;
  };

  /**
   * @MethodName order
   *
   * @author yefeng {@date 2024-01-19 15:39:55}
   * @since 1.0
   * @return int 订单
   */
  default int order() {
    return 100;
  }
}
