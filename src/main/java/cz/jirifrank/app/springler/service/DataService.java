package cz.jirifrank.app.springler.service;

import cz.jirifrank.app.springler.model.dto.HumidityMeasurement;
import cz.jirifrank.app.springler.model.dto.Log;
import cz.jirifrank.app.springler.model.statics.Action;

import java.util.List;

/**
 * Created by frankj on 5.4.2017.
 */
public interface DataService {
	void persistHumidity(Double humidity);

	void persistLog(Action action, String value);

	List<HumidityMeasurement> getLatest();

	HumidityMeasurement getLast();

	List<Log> getLatestLogs();
}
