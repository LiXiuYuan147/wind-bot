package xx.wind.app.botConfig.qylc;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * 黔云打卡配置
 *
 * @see woaini.fenger.botConfig.qylc.QylcAppConfig
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
@FieldNameConstants
public class QylcAppConfig implements Serializable {

  /**
   * @see String 配置键
   */
  public static final String CONFIG_KEY = "qylc.app";

  /**
   * @see String 品牌xiaomi
   */
  private String brand;

  /**
   * @see String 型号M2102J2SC
   */
  private String model;

  /**
   * @see String 操作系统版本Android 11
   */
  private String osVersion;

  /**
   * @see String CID apple://73DB16A0-CD02-45BD-A16A-BCC00CF6EBB5
   */
  private String cid;

  /**
   * @see String 设备ID 73DB16A0-CD02-45BD-A16A-BCC00CF6EBB5
   */
  private String deviceId;

  /**
   * @see String 应用程序版本 1.5.8
   */
  private String appVersion;

  /**
   * @see String 设备名称 android
   */
  private String deviceName;

  /**
   * @see String 登录名
   */
  private String loginName;

  /**
   * @see String 密码
   */
  private String password;

  private String jwt;
}
