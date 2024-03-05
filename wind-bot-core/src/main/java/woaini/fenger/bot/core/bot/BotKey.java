package woaini.fenger.bot.core.bot;

import lombok.Data;

import java.io.Serializable;

/**
 * 机器人的唯一标识
 *
 * @see woaini.fenger.bot.core.bot.BotKey
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class BotKey implements Serializable {

    /**
     * @see String 自身id 例如qq号
     */
    private String selfId;

    /**
     * @see String 对应的平台 所有平台以小写为主
     */
    private String platForm;

    public BotKey(String selfId, String platForm) {
        this.selfId = selfId;
        this.platForm = platForm;
    }

    public static BotKey of(String selfId, String platForm) {
        return new BotKey(selfId, platForm);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        BotKey botKey = (BotKey) object;

        if (!selfId.equals(botKey.selfId)) {
            return false;
        }
        return platForm.equals(botKey.platForm);
    }

    @Override
    public int hashCode() {
        int result = selfId.hashCode();
        result = 31 * result + platForm.hashCode();
        return result;
    }
}
