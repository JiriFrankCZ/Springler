package cz.jirifrank.app.springler.controller;

import cz.jirifrank.app.springler.config.Application;
import cz.jirifrank.app.springler.model.dto.HumidityMeasurement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = Application.class
)
public class DataCollectorControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void baseTest(){
		HumidityMeasurement humidityMeasurement = new HumidityMeasurement();
		humidityMeasurement.setValue(745.5);
		humidityMeasurement.setDateTime(LocalDateTime.now());

		restTemplate.postForLocation("/data", humidityMeasurement);
	}
}
