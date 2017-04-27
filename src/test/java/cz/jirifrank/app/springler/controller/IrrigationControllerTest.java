package cz.jirifrank.app.springler.controller;

import cz.jirifrank.app.springler.config.Application;
import cz.jirifrank.app.springler.model.dto.WeatherInfo;
import cz.jirifrank.app.springler.service.WeatherService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = Application.class
)
@ActiveProfiles("test")
public class IrrigationControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void baseTest() {
		restTemplate.postForLocation("/humidity/{data}", null, "10.5");
	}

	@Test
	public void rainProbabilityTest() {
		WeatherInfo weatherInfo = weatherService.getActualForecast();
		boolean rain = (boolean) restTemplate.getForObject(URI.create("/weather/rain"), Map.class).get("rain");
		Assert.assertEquals(weatherInfo.getRainProbability() > 80, rain);
	}
}