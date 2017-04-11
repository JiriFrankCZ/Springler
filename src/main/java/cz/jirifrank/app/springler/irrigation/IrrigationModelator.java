package cz.jirifrank.app.springler.irrigation;

import cz.jirifrank.app.springler.model.dto.WeatherInfo;
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

	@Autowired
	private WeatherService weatherService;

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

	@Scheduled(fixedDelay = 10 * 1000)
	public void updateForeacast(){
		log.info("Updating model");
		currentWeatherInfo = weatherService.getForecast(positionLatitude, positionLongitude);
		log.info("Model updated");
	}


	@Scheduled(fixedDelay = 15 * 1000)
	public void recalculate(){

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
