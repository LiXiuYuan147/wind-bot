package woaini.fenger.bot.core.json;

import com.alibaba.fastjson2.JSONPath;

/**
 * JsonResult
 *
 * @see JSONResult
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public class JSONResult {

  /**
   * @see String JSON字符串
   */
  private String jsonStr;

  /**
   * @see Boolean 成功
   */
  private Boolean success;

  public JSONResult(String jsonStr) {
    this.jsonStr = jsonStr;
    this.success = false;
  }

  public Object getPathValue(String path) {
    return JSONPath.eval(jsonStr, path);
  }
}
