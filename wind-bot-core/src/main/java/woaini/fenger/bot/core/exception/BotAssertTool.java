package woaini.fenger.bot.core.exception;

import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;
import woaini.fenger.bot.core.exception.enums.BotExceptionType;


/**
 * 机器人断言工具
 * @see  woaini.fenger.bot.core.exception.BotAssertTool
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */@UtilityClass
public class BotAssertTool {

    public void isActualTrue(boolean exe, BotExceptionType exceptionType, String template, Object ...args){
        if (exe){
            throw new BotException(exceptionType, StrUtil.format(template,args));
        }
    }
}
