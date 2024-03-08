package woaini.fenger.bot.core.boot;

import java.util.List;
import woaini.fenger.bot.core.bot.Bot;

/**
 * IBot自动注册 必须实现改接口达到自动扫描并且注册bot
 *
 * @see IBotAutoRegister
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public interface IBotAutoRegister {

  /**
   * @MethodName name
   *
   * @author yefeng {@date 2024-03-08 14:13:16}
   * @since 1.0
   * @return {@link String } 名字
   */
  String name();

  /**
   * @MethodName autoRegister
   *
   * @author yefeng {@date 2024-03-08 14:13:22}
   * @since 1.0
   * @return {@link List }<{@link Bot }> 自动注册
   */
  List<Bot> autoRegister();
}
