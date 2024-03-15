package xx.wind.app.command.amap.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "amap_city")
public class AmapCityEntity {
    /**
     * @see Integer ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cityName;

    private String cityCode;

    private String adcode;
}
