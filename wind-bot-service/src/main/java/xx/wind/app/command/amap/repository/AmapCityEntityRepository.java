package xx.wind.app.command.amap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xx.wind.app.command.amap.entity.AmapCityEntity;

@Repository
public interface AmapCityEntityRepository extends JpaRepository<AmapCityEntity, Integer> {}
