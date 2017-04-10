package cz.jirifrank.app.springler.service;

import cz.jirifrank.app.springler.model.statics.ActivitySeverity;

/**
 * Created by FrankJ on 10.4.2017.
 */
public interface IrrigationService {
	boolean isAllowedEmergency();
	void performWatering(ActivitySeverity severity);
}
