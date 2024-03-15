package xx.wind.app.command.dogLickerDiary;

import org.springframework.stereotype.Component;
import woaini.fenger.bot.core.command.ICmd;
import woaini.fenger.bot.core.command.anno.SubCmd;
import woaini.fenger.bot.core.session.Session;
import xx.wind.app.command.dogLickerDiary.api.DogLickerDiaryApi;

/**
 * 狗舔日记
 * @see  woaini.fenger.command.dogLickerDiary.DogLickerDiaryCmd
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */
@Component
public class DogLickerDiaryCmd implements ICmd {
    @Override
    public String masterCmdName() {
        return "舔狗日记";
    }

    @Override
    public String description() {
        return "生成舔狗日记";
    }


    @SubCmd
    public void execute(Session session) {
        session.replyMessage(DogLickerDiaryApi.getDogLickerDiary());
    }
}
