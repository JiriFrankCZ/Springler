package cz.jirifrank.app.springler.irrigation;

import cz.jirifrank.app.springler.model.dto.WeatherInfo;
import cz.jirifrank.app.springler.service.ExperienceService;
import cz.jirifrank.app.springler.service.WeatherService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

/**
 * Created by frankj on 5.4.2017.
 */
@Component
@Slf4j
public class IrrigationModelator {

	@Value("${location.latitude}")
	private String positionLongitude;

	@Value("${location.longitude}")
	private String positionLatitude;

	@Value("${watering.rain.probabilityThreshold}")
	private Double rainProbabilityThreshold;

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private ExperienceService experienceService;

	private WeatherInfo currentWeatherInfo;

	private Model model;

	@PostConstruct
	public void init(){
		log.info("Irrigation modelator started initialization.");

		model = new Model();
		updateForeacast();
		recalculate();

		log.info("Model is ready for publish.");
	}

	@Scheduled(fixedDelay = 15 * 60 * 1000)
	public void updateForeacast(){
		log.info("Updating forecast from remote webservice.");
		currentWeatherInfo = weatherService.getForecast(positionLatitude, positionLongitude);
		log.info("Forecast is actual.");
	}


	@Scheduled(fixedDelay = 30 * 60 * 1000)
	public void recalculate(){
		log.info("Updating model according to current conditions.");
		if (currentWeatherInfo != null) {
			model.setSunrise(currentWeatherInfo.getSunrise());
			model.setSunset(currentWeatherInfo.getSunset());
			if (currentWeatherInfo.getRainProbability() >= rainProbabilityThreshold) {
				model.setDuration(calculateWateringDuration());
			} else {
				model.setDuration(0);
			}
		} else {
			model.setDuration(calculateWateringDuration());
		}
		log.info("Model is ready for distribution.");
	}

	private int calculateWateringDuration() {
		return 0;
	}

	@Scheduled(fixedDelay = 60 * 60 * 1000)
	public void improve() {
		log.info("Self improvement phase started.");

		experienceService.exam();

		log.info("Machine improvement has been done.");
	}

	public Model getModel() {
		return model;
	}
}

@Data
class Model{
	private LocalDateTime sunrise;
	private LocalDateTime sunset;
	private Integer duration;
}
