package cz.jirifrank.app.springler.service;

import cz.jirifrank.app.springler.model.statics.ExperiencePeriod;

/**
 * Created by FrankJ on 11.4.2017.
 */
public interface ExperienceService {
	void create(Double actualTemperature, Double expectedTemperature, ExperiencePeriod period, Double humidity, Integer duration);

	void exam();
}
