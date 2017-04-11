package cz.jirifrank.app.springler.repository;

import cz.jirifrank.app.springler.model.entity.Humidity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HumidityRepository extends CrudRepository<Humidity, Long>{
	List<Humidity> findAllByDateTimeBetweenOrderByDateTimeDesc(LocalDateTime from, LocalDateTime to);
}
