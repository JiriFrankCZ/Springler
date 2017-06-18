package cz.jirifrank.app.springler.controller;

import cz.jirifrank.app.springler.model.dto.HumidityMeasurement;
import cz.jirifrank.app.springler.model.dto.WeatherInfo;
import cz.jirifrank.app.springler.service.DataService;
import cz.jirifrank.app.springler.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Data collection controller for storing all the incomming data
 *
 * @author Jiří Frank
 */
@RestController
@Slf4j
public class IrrigationController {

	private AtomicInteger wateringRequest;

	@Autowired
	private DataService dataService;

	@Autowired
	private WeatherService weatherService;

	@PostConstruct
	private void init(){
		wateringRequest = new AtomicInteger(0);

		log.info("Irrigation ready.");
	}

	@RequestMapping(value = "/humidity/{data}", method = RequestMethod.POST)
	public void uploadData(@PathVariable("data") Double soilMoistureHumidity) {
		log.info("New humidity data [{}].", soilMoistureHumidity);
		dataService.persistHumidity(soilMoistureHumidity);
		log.info("Humidity data has been saved.");
	}

	@RequestMapping(value = "/weather/rain", method = RequestMethod.GET)
	public Map<String, Boolean> rainCheck() {
		log.info("Rain probability request.");

		WeatherInfo actualForecast = weatherService.getActualForecast();
		boolean rain = actualForecast.getRainProbability() > 80;

		log.info("Probability resolved : {}", rain);

		return Collections.singletonMap("rain", rain);
	}

	@RequestMapping(value = "/humidity", method = RequestMethod.GET)
	public HumidityMeasurement latestHumidity() {
		log.info("Data list request arrived.");
		return dataService.getLast();
	}


	@RequestMapping(value = "/humidity/latest", method = RequestMethod.GET)
	public List<HumidityMeasurement> list() {
		log.info("Data list request arrived.");
		return dataService.getLatest();
	}

	@RequestMapping(value = "/watering/{duration}", method = RequestMethod.POST)
	public void wateringRequest(@PathVariable("duration") Integer duration) {
		log.info("Watering request for {} seconds.", duration);
		wateringRequest.addAndGet(duration);
		log.info("Watering has been set.");
	}

	@RequestMapping(value = "/watering", method = RequestMethod.GET)
	public Map<String, Integer> wateringRequestInfo() {
		log.info("Watering request.");

		int wateringRequest = this.wateringRequest.get();
		this.wateringRequest.set(0);

		log.info("Watering duration {} returned.", wateringRequest);
		return Collections.singletonMap("duration", wateringRequest);
	}
}