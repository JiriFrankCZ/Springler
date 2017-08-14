package cz.jirifrank.app.springler.service.impl;

import cz.jirifrank.app.springler.model.dto.WeatherInfo;
import cz.jirifrank.app.springler.model.dto.weather.DarkSkyResponse;
import cz.jirifrank.app.springler.model.dto.weather.Data;
import cz.jirifrank.app.springler.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;

@Service
@Slf4j
public class DarkSkyWeatherService implements WeatherService {

	private static final String BASE_PATH = "https://api.darksky.net/forecast";

	private static final String STATIC_PARAMS = "exclude=currently,hourly,minutely,alerts,flags&lang=cs&units=si";

	private static final String PRECIPE_TYPE_RAIN = "rain";

	@Value("${location.key}")
	private String secretKey;

	@Value("${location.latitude}")
	private String longitude;

	@Value("${location.longitude}")
	private String latitude;

	private RestTemplate restTemplate = new RestTemplate();

	private WeatherInfo actualForecast;

	@PostConstruct
	public void init() {
		updateForeacast();
	}

	@Override
	public WeatherInfo getForecast() {
		log.info("Weather info request for {} and {} position.", latitude, longitude);

		StringBuilder sb = new StringBuilder();
		sb.append(BASE_PATH);
		sb.append("/");
		sb.append(secretKey);
		sb.append("/");
		sb.append(longitude);
		sb.append(",");
		sb.append(latitude);
		sb.append("?");
		sb.append(STATIC_PARAMS);

		final String url = sb.toString();

		log.debug("Url for request has been built {}.", url);

		ResponseEntity<DarkSkyResponse> responseEntity = restTemplate.getForEntity(URI.create(url), DarkSkyResponse.class);

		if(responseEntity.getStatusCode().is2xxSuccessful()) {
			log.info("Weather info request was successful.");
			
			Data data = responseEntity.getBody().getDaily().getData().get(0);
			
			log.info("Precipe type: {} with probability.", data.getPrecipType(), data.getPrecipProbability());
			
			WeatherInfo weatherInfo = new WeatherInfo();
			weatherInfo.setMaxTemperature(data.getTemperatureMax());
			weatherInfo.setMinTemperature(data.getTemperatureMin());
			weatherInfo.setSunrise(data.getSunriseTime());
			weatherInfo.setSunset(data.getSunsetTime());
			weatherInfo.setRainProbability((data.getPrecipType() != null && data.getPrecipType().equalsIgnoreCase(PRECIPE_TYPE_RAIN)) ? data.getPrecipProbability() : 0);

			log.debug("Weather data {}.", weatherInfo);

			return weatherInfo;
		}else{
			log.error("Error occured during call of Dark Sky API.", responseEntity.getStatusCode());
			return null;
		}
	}

	@Scheduled(fixedDelay = 60 * 60 * 1000)
	public void updateForeacast() {
		log.info("Updating forecast from remote webservice.");
		actualForecast = getForecast();
		log.info("Forecast is actual.");
	}

	public WeatherInfo getActualForecast() {
		return actualForecast;
	}
}
