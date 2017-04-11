package cz.jirifrank.app.springler.model.entity;

import cz.jirifrank.app.springler.model.statics.ExperiencePeriod;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Knowledge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "period")
	@Enumerated(EnumType.STRING)
	private ExperiencePeriod period;

	@Column(name = "duration")
	private Integer duration;

	@Column(name = "humidity")
	private Double humidity;

	@Column(name = "expected_temperature")
	private Double expectedTemperature;

	@Column(name = "actual_temperature")
	private Double actualTemperature;
}
