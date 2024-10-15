package xx.wind.app.command.qylc.api;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSONObject;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import woaini.fenger.bot.core.exception.BotAssertTool;
import woaini.fenger.bot.core.exception.enums.BotExceptionType;
import woaini.fenger.bot.core.json.JSONResult;
import woaini.fenger.bot.core.utils.StringTemplateUtils;
import xx.wind.app.botConfig.qylc.QylcAppConfig;

/**
 * 在API中打卡
 *
 * @see QylcApi
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@UtilityClass
@Slf4j
public class QylcApi {

  /**
   * @see String 主机
   */
  private static final String HOST = "http://qylcapi.dev.chain-idea.com";

  private static final double minLat = 26.616568; // 最小纬度
  private static final double maxLat = 26.620484; // 最大纬度
  private static final double minLng = 106.642597; // 最小经度
  private static final double maxLng = 106.645061; // 最大经度

  private static double tmpLat = 26.617473126291234;
  private static double tmpLng = 106.64272421888121;

  /**
   * @MethodName login
   *
   * @param userConfig 用户配置
   * @author yefeng {@date 2024-01-30 09:16:08}
   * @since 1.0
   * @return {@link JSONResult } 登录
   */
  public JSONResult login(QylcAppConfig userConfig) {

    String data =
        """
          {
                "deviceInfo": "手机品牌：${userConfig.brand},手机型号：${userConfig.model},操作系统版本：${userConfig.osVersion}",
                "username": "${userConfig.loginName}",
                "password": "${userConfig.password}",
                "cid": "${userConfig.brand}://${userConfig.cid}",
                "deviceName": "${userConfig.deviceName}",
                "deviceId": "${userConfig.deviceId}",
                "appVersion": "${userConfig.appVersion}"
            }
          """;
    data =
        StringTemplateUtils.execString(
            QylcAppConfig.CONFIG_KEY,
            data,
            MapBuilder.create(new HashMap<String, Object>()).put("userConfig", userConfig).build());

    String body =
        HttpRequest.post(HOST + "/api/app/user/public/login/account").body(data).execute().body();
    return handlerResult(body);
  }

  /**
   * @MethodName verifyClockInLog
   *
   * @param userConfig 用户配置
   * @param jwt JWT
   * @param schedulingId 计划ID
   * @param up 向上
   * @author yefeng {@date 2024-01-30 10:06:01}
   * @since 1.0
   * @return {@link JSONResult } 校验是否能够打卡
   */
  public JSONResult verifyClockInLog(
      QylcAppConfig userConfig, String jwt, String schedulingId, boolean up) {

    JSONObject data = new JSONObject();
    data.put("clockInType", up ? "IN" : "OUT");
    data.put("day", DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN));
    data.put("latitude", tmpLat);
    data.put("longitude", tmpLng);
    data.put("time", DateUtil.format(new Date(), DatePattern.NORM_TIME_PATTERN));
    data.put("todaySteps", 0);
    data.put("schedulingId", schedulingId);
    data.put("deviceId", userConfig.getDeviceId());

    String body =
        HttpRequest.post(HOST + "/api/app/work/clockInApi/verifyClockInLog")
            .headerMap(getDefaultHeader(userConfig, jwt), true)
            .body(data.toJSONString())
            .execute()
            .body();
    return handlerResult(body);
  }

  /**
   * @MethodName saveClockInLog
   *
   * @param userConfig 用户配置
   * @param jwt JWT
   * @param schedulingId 计划ID
   * @param up 向上
   * @author yefeng {@date 2024-01-30 10:14:40}
   * @since 1.0
   * @return {@link JSONResult } 实际打卡
   */
  public JSONResult saveClockInLog(
      QylcAppConfig userConfig, String jwt, String schedulingId, boolean up) {

    JSONObject data = new JSONObject();
    data.put("clockInType", up ? "IN" : "OUT");
    data.put("day", DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN));
    data.put("latitude", tmpLat);
    data.put("longitude", tmpLng);
    data.put("time", DateUtil.format(new Date(), DatePattern.NORM_TIME_PATTERN));
    data.put("todaySteps", 0);
    data.put("schedulingId", schedulingId);
    data.put("deviceId", userConfig.getDeviceId());

    String body =
        HttpRequest.post(HOST + "/api/app/work/clockInApi/saveClockInLog")
            .body(data.toJSONString())
            .headerMap(getDefaultHeader(userConfig, jwt), true)
            .execute()
            .body();
    return handlerResult(body);
  }

  /**
   * @MethodName schedulingUserList
   *
   * @param userConfig 用户配置
   * @param jwt JWT
   * @author yefeng {@date 2024-01-30 09:26:06}
   * @since 1.0
   * @return {@link JSONResult } 获取计划打卡班次
   */
  public JSONResult schedulingUserList(QylcAppConfig userConfig, String jwt) {

    var url = HOST + "/api/app/work/clockInApi/schedulingUserList";
    String body =
        HttpRequest.get(url).headerMap(getDefaultHeader(userConfig, jwt), true).execute().body();

    return handlerResult(body);
  }

  /**
   * @MethodName getClockInType
   *
   * @param userConfig 用户配置
   * @param jwt JWT
   * @author yefeng {@date 2024-01-30 10:01:09}
   * @since 1.0
   * @return {@link JSONResult } 获取打卡类型
   */
  public JSONResult getClockInType(QylcAppConfig userConfig, String jwt) {

    var url = HOST + "/api/app/work/clockInApi/getClockInType";
    String body =
        HttpRequest.get(url).headerMap(getDefaultHeader(userConfig, jwt), true).execute().body();

    return handlerResult(body);
  }

  /**
   * @MethodName getDefaultHeader
   *
   * @param userConfig 用户配置
   * @param jwt JWT
   * @author yefeng {@date 2024-01-30 09:19:40}
   * @since 1.0
   * @return {@link Map }<{@link String }, {@link String }> 获取默认请求头
   */
  public Map<String, String> getDefaultHeader(QylcAppConfig userConfig, String jwt) {

    return MapBuilder.create(new HashMap<String, String>())
        .put("Content-Type", "application/json")
        .put("Cid", StrUtil.format("{}://{}", userConfig.getBrand(), userConfig.getCid()))
        .put("Client-Id", userConfig.getCid())
        .put("Platform", userConfig.getDeviceName())
        .put("Authorization", jwt)
        .build();
  }

  private JSONResult handlerResult(String body) {
    BotAssertTool.isActualTrue(
        StrUtil.isEmpty(body), BotExceptionType.BOT_RPC_PARAMS, "http请求失败,返回值为空");
    JSONResult result = new JSONResult(body);
    // 获取是否失败
    Boolean error = (Boolean) result.getPathValue("$.error");
    String msg = (String) result.getPathValue("$.msg");
    BotAssertTool.isActualTrue(error, BotExceptionType.BOT_RPC_PARAMS, "异常:\r\n{}", msg);
    return result;
  }

  /**
   * @MethodName randomLocation
   *
   * @author yefeng {@date 2024-01-30 10:15:34}
   * @since 1.0
   *     <p>随机位置 打卡区域内随机位置
   */
  public void randomLocation() {
    tmpLat = RandomUtil.randomDouble(minLat, maxLat, 13, RoundingMode.UP);
    tmpLng = RandomUtil.randomDouble(minLng, maxLng, 13, RoundingMode.UP);
  }

  /**
   * 本周菜单
   * @param userConfig 配置
   * @param jwt jwt
   * @author yefeng {@date 2024-10-15 11:24:47}
   * @since 1.0
   * @return {@link JSONResult }
   */
  public JSONResult foodMenu(QylcAppConfig userConfig, String jwt) {
    var url = HOST + "/api/mddApp/activity/activityStatistics/activityStatisticsWeekMenu";
    String body =
        HttpRequest.get(url).headerMap(getDefaultHeader(userConfig, jwt), true).execute().body();
    return handlerResult(body);
  }

  /**
   * 校验token是否有效的接口
   * @param userConfig 配置
   * @param jwt jwt
   * @author yefeng {@date 2024-10-15 11:24:47}
   * @since 1.0
   * @return {@link JSONResult }
   */
  public boolean verifyActivityStatistics(QylcAppConfig userConfig, String jwt) {
    var url = HOST + "/api/mddApp/activity/activityStatistics/verifyActivityStatistics";
    HttpResponse execute = HttpRequest.get(url).headerMap(getDefaultHeader(userConfig, jwt), true).execute();
    return execute.getStatus() != 401;
  }
}
