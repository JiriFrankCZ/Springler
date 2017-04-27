package cz.jirifrank.app.springler.service;

import cz.jirifrank.app.springler.model.dto.HumidityMeasurement;

import java.util.List;

/**
 * Created by frankj on 5.4.2017.
 */
public interface DataService {
	void persistHumidity(Double humidity);

	List<HumidityMeasurement> getLatest();
}
