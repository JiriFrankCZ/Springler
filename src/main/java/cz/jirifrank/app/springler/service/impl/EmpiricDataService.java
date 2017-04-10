package cz.jirifrank.app.springler.service.impl;

import cz.jirifrank.app.springler.model.dto.HumidityMeasurement;
import cz.jirifrank.app.springler.model.entity.Activity;
import cz.jirifrank.app.springler.model.entity.Humidity;
import cz.jirifrank.app.springler.model.statics.ActivityResult;
import cz.jirifrank.app.springler.model.statics.ActivitySeverity;
import cz.jirifrank.app.springler.model.statics.ActivityType;
import cz.jirifrank.app.springler.repository.ActivityRepository;
import cz.jirifrank.app.springler.repository.HumidityRepository;
import cz.jirifrank.app.springler.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Slf4j
public class EmpiricDataService implements DataService {

	private static final DozerBeanMapper MAPPER;

	@Autowired
	private HumidityRepository humidityRepository;

	@Autowired
	private ActivityRepository activityRepository;

	static {
		MAPPER = new DozerBeanMapper();
		MAPPER.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));
	}

	@Override
	@Transactional
	public void persistHumidity(HumidityMeasurement humidityMeasurement) {
		Humidity humidity = MAPPER.map(humidityMeasurement, Humidity.class);
		if(humidity == null){
			log.error("Error occured during mapping from DTO to Entity.", humidityMeasurement);
			return;
		}

		humidityRepository.save(humidity);

		Activity activity = new Activity();
		activity.setDateTime(LocalDateTime.now());
		activity.setSeverity(ActivitySeverity.STANDARD);
		activity.setResult(ActivityResult.SUCCESS);
		activity.setType(ActivityType.DATA_REPORT);
		activityRepository.save(activity);
	}
}
