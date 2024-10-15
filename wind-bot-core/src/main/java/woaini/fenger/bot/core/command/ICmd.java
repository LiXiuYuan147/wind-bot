package woaini.fenger.bot.core.command;

/** 标识这个类是一个命令 */
public interface ICmd {
  /**
   * @MethodName value 匹配的命令 主命令
   *
   * @author yefeng {@date 2024-01-19 15:17:42}
   * @since 1.0
   * @return {@link String } 值
   */
  String masterCmdName();

  /**
   * @MethodName description 命令描述
   *
   * @author yefeng {@date 2024-01-19 15:22:29}
   * @since 1.0
   * @return {@link String } 描述
   */
  String description();

  /**
   * 是否需要权限
   *
   * @author yefeng {@date 2024-10-15 09:47:30}
   * @since 1.0
   * @return boolean
   */
  default boolean auth() {
    return false;
  }
}
