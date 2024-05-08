package woaini.fenger.bot.code.impl.onebot11;

import woaini.fenger.bot.code.impl.onebot11.adapter.OneBot11WsAdapter;
import woaini.fenger.bot.code.impl.onebot11.config.OneBot11Config;
import woaini.fenger.bot.code.impl.onebot11.internal.OneBot11Internal;
import woaini.fenger.bot.core.adapter.Adapter;
import woaini.fenger.bot.core.bot.Bot;
import woaini.fenger.bot.core.bot.enums.ConnectType;
import woaini.fenger.bot.core.internal.Internal;

/**
 * 一个机器人11 
 *
 * @see woaini.fenger.bot.code.impl.onebot11.OneBot11
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
public class OneBot11 extends Bot<OneBot11Config> {

  public static final String NAME = "OneBot11";

  private Adapter sendAdapter;

  private Adapter receiveAdapter;

  public OneBot11(OneBot11Config config) {
    super(config);
    init();
  }

  @Override
  public String agreement() {
    return OneBot11.NAME;
  }

  @Override
  public void close() {

  }

  @Override
  public Adapter receiveAdapter() {
    return receiveAdapter;
  }

  @Override
  public Adapter sendAdapter() {
    return sendAdapter;
  }

  @Override
  public Internal internal() {
    return new OneBot11Internal(this);
  }

  public void init(){
    OneBot11Config config = this.getConfig();
    if (config.getSendConnectType().equals(config.getReceiveConnectType())){
      Adapter adapter = getAdapter(config.getSendConnectType());
      this.sendAdapter = adapter;
      this.receiveAdapter = adapter;
    }else {
      this.sendAdapter = getAdapter(config.getSendConnectType());
      this.receiveAdapter = getAdapter(config.getReceiveConnectType());
    }
  }

  private Adapter getAdapter(ConnectType connectType){
    switch (connectType){
      case WS:
        return new OneBot11WsAdapter(this);
      default:
        return null;
    }
  }
}
