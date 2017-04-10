package cz.jirifrank.app.springler.controller;

import cz.jirifrank.app.springler.model.dto.HumidityMeasurement;
import cz.jirifrank.app.springler.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Data collection controller for storing all the incomming data
 *
 * @author Jiří Frank
 */
@RestController
@RequestMapping("/data")
@Slf4j
public class DataCollectorController {

	@Autowired
	private DataService dataService;

	@RequestMapping(method = RequestMethod.POST)
	public void uploadData(@Valid @RequestBody HumidityMeasurement humidityMeasurement){
		log.info("Uploading new data");
		dataService.persistHumidity(humidityMeasurement);
	}
}
