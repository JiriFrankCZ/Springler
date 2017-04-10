package cz.jirifrank.app.springler.controller;

import cz.jirifrank.app.springler.model.dto.IrrigationCommand;
import cz.jirifrank.app.springler.model.dto.ThresholdMonitoringCommand;
import cz.jirifrank.app.springler.model.dto.TimerCommand;
import cz.jirifrank.app.springler.service.DataService;
import cz.jirifrank.app.springler.service.IrrigationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/irrigation")
@Slf4j
public class IrrigationController {

	@Autowired
	private DataService dataService;

	@Autowired
	private IrrigationService irrigationService;

	@Value("watering.duration.emergency")
	private String emergencyIrrigationDuration;

	@Value("watering.humidity.critical")
	private String criticalHumidityThreshold;

	@Value("watering.humidity.low")
	private String lowHumidityThreshold;

	@Value("watering.humidity.ideal")
	private String idealHumidityThreshold;

	@RequestMapping(method = RequestMethod.POST)
	public IrrigationCommand emergencyRequest(){
		log.warn("Emergency watering request started.");
		IrrigationCommand irrigationCommand = new IrrigationCommand();

		if(irrigationService.isAllowedEmergency()){

		}else{
			irrigationCommand.setDuration(0d);
		}

		log.warn("Emergency watering request finished.");
		return irrigationCommand;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public IrrigationCommand standardRequest(){
		log.info("Standard watering request started.");
		IrrigationCommand irrigationCommand = new IrrigationCommand();

		log.info("Standard watering request finished.");
		return irrigationCommand;
	}

	@RequestMapping(value = "config/clock", method = RequestMethod.GET)
	public TimerCommand clockRequest(){
		log.info("Clock config started.");
		TimerCommand timerCommand = new TimerCommand();


		log.info("Clock config finished.");
		return timerCommand;
	}

	@RequestMapping(value = "config/threshold", method = RequestMethod.GET)
	public ThresholdMonitoringCommand thresholdsReuqest(){
		log.info("Clock config started.");
		ThresholdMonitoringCommand thresholdMonitoringCommand = new ThresholdMonitoringCommand();

		log.info("Clock config finished.");
		return thresholdMonitoringCommand;
	}

}


