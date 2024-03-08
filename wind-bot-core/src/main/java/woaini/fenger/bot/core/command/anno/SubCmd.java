package woaini.fenger.bot.core.command.anno;

import java.lang.annotation.*;


/**
 * Subcmd
 * @see  woaini.fenger.bot.core.command.anno.SubCmd
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SubCmd {
    String DEFAULT_VALUE = "default";

    String value() default DEFAULT_VALUE;

  /**
   * @MethodName description 子命令描述
   *
   * @author yefeng {@date 2024-01-19 15:23:04}
   * @since 1.0
   * @return {@link String } 描述
   */
  String description() default "";
}
