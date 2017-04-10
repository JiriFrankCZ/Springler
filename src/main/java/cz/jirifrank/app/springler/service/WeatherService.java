package cz.jirifrank.app.springler.service;

import cz.jirifrank.app.springler.model.dto.WeatherInfo;

/**
 * Created by frankj on 5.4.2017.
 */
public interface WeatherService {
	WeatherInfo getForecast(String latitude, String longitude);
}
