package cz.jirifrank.app.springler.service.impl;

import cz.jirifrank.app.springler.model.entity.Experience;
import cz.jirifrank.app.springler.model.entity.Humidity;
import cz.jirifrank.app.springler.model.statics.ExperiencePeriod;
import cz.jirifrank.app.springler.model.statics.LearningAction;
import cz.jirifrank.app.springler.repository.ActivityRepository;
import cz.jirifrank.app.springler.repository.ExperienceRepository;
import cz.jirifrank.app.springler.repository.HumidityRepository;
import cz.jirifrank.app.springler.service.ExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@Transactional
public class MachineExperienceService implements ExperienceService {

	private static final int PERIOD_LENGTH = 12;

	private static final double CRITICAL_HUMIDITY_WEIGHT = 0.3;

	private static final double LOW_HUMIDITY_WEIGHT = 0.1;

	@Value("${watering.humidity.critical}")
	private double criticalHumidityThreshold;

	@Value("${watering.humidity.low}")
	private double lowHumidityThreshold;

	@Value("${watering.humidity.ideal}")
	private double idealHumidityThreshold;

	@Value("${watering.learning.changeThreshold}")
	private int changeThreshold;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Autowired
	private HumidityRepository humidityRepository;

	@Autowired
	private ActivityRepository activityRepository;

	@Override
	public void create(Double actualTemperature, Double expectedTemperature, ExperiencePeriod period, Double humidity, Integer duration) {
		Experience experience = new Experience();
		experience.setPeriod(period);
		experience.setActualTemperature(actualTemperature);
		experience.setExpectedTemperature(expectedTemperature);
		experience.setHumidity(humidity);
		experience.setDuration(duration);
		experience.setLearningAction(LearningAction.TO_IMPROVE);
		experience.setDateTime(LocalDateTime.now());
		experienceRepository.save(experience);
	}

	@Override
	public void exam() {
		List<Experience> experienceList = experienceRepository.findAllByLearningAction(LearningAction.TO_IMPROVE);
		for (Experience experience : experienceList) {
			log.info("Examination of experience. {}", experience.toString());

			LocalDateTime from = experience.getDateTime();
			LocalDateTime to = from.plusHours(PERIOD_LENGTH);

			LocalDateTime criticalHumidity = null;
			LocalDateTime lowHumidity = null;

			List<Humidity> humidityList = humidityRepository.findAllByDateTimeBetweenOrderByDateTimeDesc(from, to);
			for (Humidity humidity : humidityList) {
				if (humidity.getValue() < criticalHumidityThreshold) {
					log.debug("Critical humidity level detected.");
					criticalHumidity = humidity.getDateTime();
					break;
				} else if (humidity.getValue() < lowHumidityThreshold) {
					log.debug("Low humidity level detected.");
					lowHumidity = humidity.getDateTime();
				}
			}

			double change = 0;
			if (criticalHumidity != null) {
				change = changeThreshold * CRITICAL_HUMIDITY_WEIGHT * ((double) ChronoUnit.HOURS.between(from, criticalHumidity) / PERIOD_LENGTH);
			} else if (lowHumidity != null) {
				change = changeThreshold * LOW_HUMIDITY_WEIGHT * ((double) ChronoUnit.HOURS.between(from, lowHumidity) / PERIOD_LENGTH);
			}

			experience.setLearningAction(LearningAction.NO_ACTION);
			log.info("Suggested change {}", change);
		}

		experienceRepository.save(experienceList);
	}
}
