package cz.jirifrank.app.springler.model.dto;

import lombok.Data;

@Data
public class ThresholdMonitoringCommand {
	private int humidityLow;
	private int humidityEmergency;
	private int humidityIdeal;
}
