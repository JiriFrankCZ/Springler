package cz.jirifrank.app.springler.repository;

import cz.jirifrank.app.springler.model.entity.Activity;
import cz.jirifrank.app.springler.model.statics.ActivitySeverity;
import cz.jirifrank.app.springler.model.statics.ActivityType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long>{
	List<Activity> findByTypeAndSeverityAndDateTimeAfter(ActivityType type, ActivitySeverity severity, LocalDateTime after);
}
