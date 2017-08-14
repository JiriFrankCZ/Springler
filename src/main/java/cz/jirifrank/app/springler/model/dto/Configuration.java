package cz.jirifrank.app.springler.model.dto;

import lombok.Data;

@Data
public class Configuration {
    private Integer wateringThresholdStandard;
    private Integer wateringThresholdEmergency;
}
