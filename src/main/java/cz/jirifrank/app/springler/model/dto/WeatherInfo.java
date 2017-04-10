package cz.jirifrank.app.springler.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherInfo {
	private LocalDateTime sunrise;
	private LocalDateTime sunset;
	private Double maxTemperature;
	private Double minTemperature;
	private Double rainProbability;
}
