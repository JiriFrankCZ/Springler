package cz.jirifrank.app.springler.service.impl;

import cz.jirifrank.app.springler.model.entity.Activity;
import cz.jirifrank.app.springler.model.statics.ActivityResult;
import cz.jirifrank.app.springler.model.statics.ActivitySeverity;
import cz.jirifrank.app.springler.model.statics.ActivityType;
import cz.jirifrank.app.springler.repository.ActivityRepository;
import cz.jirifrank.app.springler.service.IrrigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service
public class SophisticatedIrrigationService implements IrrigationService {

	@Value("watering.duration.emergency.frequency")
	private String emergencyFrequency;

	@Autowired
	private ActivityRepository activityRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean isAllowedEmergency() {
		return activityRepository.findByTypeAndSeverityAndDateTimeAfter(
				ActivityType.WATERING_REQUEST,
				ActivitySeverity.CRITICAL,
				LocalDateTime.now().minus(Integer.valueOf(emergencyFrequency), ChronoUnit.SECONDS))
				.size() == 0;
	}

	@Override
	public void performWatering(ActivitySeverity severity) {
		Activity activity = new Activity();
		activity.setDateTime(LocalDateTime.now());
		activity.setSeverity(severity);
		activity.setResult(ActivityResult.SUCCESS);
		activity.setType(ActivityType.WATERING_REQUEST);
		activityRepository.save(activity);
	}
}
