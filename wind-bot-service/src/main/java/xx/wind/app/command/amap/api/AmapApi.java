package xx.wind.app.command.amap.api;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import lombok.experimental.UtilityClass;
import xx.wind.app.command.amap.dto.WeatherDTO;

import static java.lang.StringTemplate.STR;

/**
 * AMAP API
 *
 * @see woaini.fenger.command.amap.api.AmapApi
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@UtilityClass
public class AmapApi {

  public static final String KEY = "bdc8380f16d22512fc45b41a0911ef40";
  /**
   * @see String 天气URL
   */
  private static final String WEATHER_URL = "https://restapi.amap.com/v3/weather/weatherInfo";

  /**
   * @MethodName weather
   * @param city 市
   * @author yefeng
   * {@date 2024-02-04 16:52:07}
   * @since 1.0
   * @return {@link WeatherDTO }
   * 天气
   */
  public WeatherDTO weather(String city) {

    String url = STR."https://restapi.amap.com/v3/weather/weatherInfo?key=\{KEY}&city=\{city}&extensions=base&output=JSON";

    String body = HttpRequest.get(url).execute().body();
    return JSONObject.parseObject(body, WeatherDTO.class);
  }
}
