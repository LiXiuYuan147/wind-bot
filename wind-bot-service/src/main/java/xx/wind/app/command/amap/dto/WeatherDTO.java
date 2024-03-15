package xx.wind.app.command.amap.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * *
 * @see  WeatherDTO
 * @author yefeng
 * {@code @Date} 2023-05-16 16:50:39
 */
@NoArgsConstructor
@Data
public class WeatherDTO implements Serializable {


    private String status;
    private String count;
    private String info;
    private String infocode;
    private List<Lives> lives;

    @NoArgsConstructor
    @Data
    public static class Lives implements Serializable {
        private String province;
        private String city;
        private String adcode;
        private String weather;
        private String temperature;
        private String winddirection;
        private String windpower;
        private String humidity;
        private String reporttime;
        private String temperature_float;
        private String humidity_float;
    }
}
