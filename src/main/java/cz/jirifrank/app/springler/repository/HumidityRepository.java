package cz.jirifrank.app.springler.repository;

import cz.jirifrank.app.springler.model.entity.Humidity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumidityRepository extends CrudRepository<Humidity, Long>{
}
