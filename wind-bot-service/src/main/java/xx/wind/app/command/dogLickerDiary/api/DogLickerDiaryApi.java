package xx.wind.app.command.dogLickerDiary.api;

import cn.hutool.http.HttpRequest;
import lombok.experimental.UtilityClass;

/**
 * 舔狗日记API
 *
 * @see woaini.fenger.command.dogLickerDiary.api.DogLickerDiaryApi
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@UtilityClass
public class DogLickerDiaryApi {

    public static final String HOST = "https://api.oick.cn/dog/api.php";

    public String getDogLickerDiary() {
        return HttpRequest.get(HOST).execute().body();
    }
}
