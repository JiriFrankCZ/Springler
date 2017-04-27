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

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Data collection controller for storing all the incomming data
 *
 * @author Jiří Frank
 */
@RestController
@Slf4j
public class IrrigationController {

	@Autowired
	private DataService dataService;

	@Autowired
	private WeatherService weatherService;

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
	public List<HumidityMeasurement> list() {
		log.info("Data list request arrived.");
		return dataService.getLatest();
	}
}
