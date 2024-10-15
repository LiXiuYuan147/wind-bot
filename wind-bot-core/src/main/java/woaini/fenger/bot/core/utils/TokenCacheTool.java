package woaini.fenger.bot.core.utils;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

import java.util.function.Function;
import java.util.function.Supplier;

@UtilityClass
public class TokenCacheTool {

  /** 默认过期时间 一周 */
  public static final int EXPIRE_SECOND = 1000 * 60 * 60 * 24 * 7;

  TimedCache<String, String> timedCache = CacheUtil.newTimedCache(EXPIRE_SECOND);

  /**
   * @param key key
   * @param expireCheckFun 是否过期校验
   * @param tokenLoadFun 加载方法
   * @author yefeng {@date 2024-10-15 11:36:57}
   * @since 1.0
   * @return {@link String }
   */
  public String getAndLoadWithKey(
      String key, Function<String, Boolean> expireCheckFun, Supplier<String> tokenLoadFun) {

    //缓存中获取是否存在
    String token = timedCache.get(key,false);
    if (StrUtil.isNotEmpty(token)){
      //存在缓存 校验是否过期
      Boolean expire = expireCheckFun.apply(token);
      if (expire){
        //过期 重新加载
        token = tokenLoadFun.get();
        timedCache.put(key,token);
      }
    }else {
      //不存在缓存 直接加载
      token = tokenLoadFun.get();
    }
    //刷新缓存过期时间
    timedCache.put(key,token);
    return token;
  }
}
