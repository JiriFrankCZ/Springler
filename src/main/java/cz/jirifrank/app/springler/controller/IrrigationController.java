package cz.jirifrank.app.springler.controller;

import cz.jirifrank.app.springler.model.dto.Configuration;
import cz.jirifrank.app.springler.model.dto.HumidityMeasurement;
import cz.jirifrank.app.springler.model.dto.WeatherInfo;
import cz.jirifrank.app.springler.service.DataService;
import cz.jirifrank.app.springler.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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
@CrossOrigin
public class IrrigationController {

	private AtomicInteger wateringRequest;

	@Autowired
	private DataService dataService;

	@Autowired
	private WeatherService weatherService;

	@Value("${watering.threshold.standard}")
	private Integer wateringThresholdStandard;

	@Value("${watering.threshold.emergency}")
	private Integer wateringThresholdEmergency;

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
		boolean rain = actualForecast.getRainProbability() > 0.6;

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

	@RequestMapping(value = "/configuration/threshold/standard/{value}", method = RequestMethod.PUT)
	public void updateConfigurationThresholdStandard(@PathVariable("value") Integer value) {
		log.info("Configuration update - threshold standard");

		this.wateringThresholdStandard = value;
	}

	@RequestMapping(value = "/configuration/threshold/emergency/{value}", method = RequestMethod.PUT)
	public void updateConfigurationThresholdEmergency(@PathVariable("value") Integer value) {
		log.info("Configuration update - threshold emergency");

		this.wateringThresholdEmergency = value;
	}

	@RequestMapping(value = "/configuration", method = RequestMethod.GET)
	public Configuration getConfiguration() {
		log.info("Configuration request");
		Configuration configuration = new Configuration();
		configuration.setWateringThresholdEmergency(wateringThresholdEmergency);
		configuration.setWateringThresholdStandard(wateringThresholdStandard);
		return configuration;
	}
}