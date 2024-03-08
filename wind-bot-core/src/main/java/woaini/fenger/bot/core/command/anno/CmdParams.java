package woaini.fenger.bot.core.command.anno;

import java.lang.annotation.*;


/**
 * CMD参数
 * @see  woaini.fenger.bot.core.command.anno.CmdParams
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CmdParams {

  /**
   * @MethodName value 匹配变量名字 比如 a 会自动匹配 -a 布尔类型会匹配--a [0] 为子参数第一个
   *
   * @author yefeng {@date 2024-01-19 15:28:25}
   * @since 1.0
   * @return {@link String } 值
   */
  String value();

  /**
   * @MethodName required
   *
   * @author yefeng {@date 2024-01-19 15:34:42}
   * @since 1.0
   * @return boolean 是否必填
   */
  boolean required() default false;

  /**
   * @MethodName defaultValue
   *
   * @author yefeng {@date 2024-01-19 15:33:49}
   * @since 1.0
   * @return {@link String } 缺省值
   */
  String defaultValue() default "";

  /**
   * @MethodName options 设定改选项只能是这几个值
   *
   * @author yefeng {@date 2024-01-19 15:29:47}
   * @since 1.0
   * @return {@link String[] } 选项
   */
  String[] options() default {};

  /**
   * @MethodName description
   *
   * @author yefeng {@date 2024-01-19 15:30:26}
   * @since 1.0
   * @return {@link String } 描述 指令描述
   */
  String description() default "";
}
