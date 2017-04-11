package cz.jirifrank.app.springler.controller;

import cz.jirifrank.app.springler.config.Application;
import cz.jirifrank.app.springler.irrigation.IrrigationModelator;
import cz.jirifrank.app.springler.model.dto.HumidityMeasurement;
import cz.jirifrank.app.springler.model.entity.Experience;
import cz.jirifrank.app.springler.model.statics.ExperiencePeriod;
import cz.jirifrank.app.springler.model.statics.LearningAction;
import cz.jirifrank.app.springler.repository.ExperienceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = Application.class
)
@ActiveProfiles("test")
public class DataCollectorControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private IrrigationModelator irrigationModelator;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Test
	public void baseTest(){
		Experience experience = new Experience();
		experience.setPeriod(ExperiencePeriod.SUNSET);
		experience.setActualTemperature(22.0);
		experience.setExpectedTemperature(20.0);
		experience.setHumidity(400.0);
		experience.setDuration(20);
		experience.setLearningAction(LearningAction.TO_IMPROVE);
		experience.setDateTime(LocalDateTime.now().minusHours(13));
		experienceRepository.save(experience);

		HumidityMeasurement humidityMeasurement = new HumidityMeasurement();
		humidityMeasurement.setValue(745.5);
		humidityMeasurement.setDateTime(LocalDateTime.now().minusHours(12));

		restTemplate.postForLocation("/data", humidityMeasurement);

		humidityMeasurement.setValue(720.0);
		humidityMeasurement.setDateTime(LocalDateTime.now().minusHours(7));

		restTemplate.postForLocation("/data", humidityMeasurement);

		humidityMeasurement.setValue(700.0);
		humidityMeasurement.setDateTime(LocalDateTime.now().minusHours(3));

		restTemplate.postForLocation("/data", humidityMeasurement);

		humidityMeasurement.setValue(400.0);
		humidityMeasurement.setDateTime(LocalDateTime.now().minusHours(2));

		restTemplate.postForLocation("/data", humidityMeasurement);

		humidityMeasurement.setValue(390.0);
		humidityMeasurement.setDateTime(LocalDateTime.now().minusHours(1));

		restTemplate.postForLocation("/data", humidityMeasurement);

		irrigationModelator.improve();
	}

	@Test
	public void baseTestLearning() {
		irrigationModelator.improve();
	}

}
