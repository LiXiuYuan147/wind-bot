package woaini.fenger.bot.core.exception;


import woaini.fenger.bot.core.exception.enums.BotExceptionType;

/**
 * BOT异常
 * @see  woaini.fenger.bot.core.exception.BotException
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */public class BotException extends RuntimeException{

    private BotExceptionType exceptionType;

    public BotException(BotExceptionType exceptionType,String message) {
        super(message);
        this.exceptionType = exceptionType;
    }
}
