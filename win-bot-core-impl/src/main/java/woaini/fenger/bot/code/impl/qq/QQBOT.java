package woaini.fenger.bot.code.impl.qq;

import cn.hutool.extra.qrcode.QrCodeUtil;
import woaini.fenger.bot.code.impl.qq.config.QQBOTConfig;
import woaini.fenger.bot.core.adapter.Adapter;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.internal.Internal;

import java.awt.image.BufferedImage;

public class QQBOT extends Bot<QQBOTConfig> {

  public static final String NAME = "QQBOT";

  public QQBOT(QQBOTConfig config) {
    super(config);
    init();
  }

  public void init() {
    //获取ws鉴权地址

  }

  @Override
  public String agreement() {
    return QQBOT.NAME;
  }

  @Override
  public void close() {

  }

  @Override
  public Adapter receiveAdapter() {
    return null;
  }

  @Override
  public Adapter sendAdapter() {
    return null;
  }

  @Override
  public Internal internal() {
    return null;
  }



}
