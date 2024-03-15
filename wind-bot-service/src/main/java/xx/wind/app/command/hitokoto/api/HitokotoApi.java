package xx.wind.app.command.hitokoto.api;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HitokotoApi {

  /**
   * @see String 主机
   */
  private static final String HOST = "https://v1.hitokoto.cn/";

  /**
   * @MethodName get
   *
   * @param type 类型
   * @author yefeng {@date 2024-02-19 17:49:32}
   * @since 1.0
   * @return {@link HitokotoResDTO } 到达 a 动画 b 漫画 c 游戏 d 文学 e 原创 f 来自网络 g 其他 h 影视 i 诗词 j 网易云 k 哲学 l
   *     抖机灵
   */
  public HitokotoResDTO get(String type) {
    if (type == null) {
      type = "";
    }
    String body = HttpRequest.get(HOST + "?c=" + type).execute().body();
    return JSONObject.parseObject(body, HitokotoResDTO.class);
  }
}
