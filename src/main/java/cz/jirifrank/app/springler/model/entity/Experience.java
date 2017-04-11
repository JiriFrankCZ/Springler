package cz.jirifrank.app.springler.model.entity;

import cz.jirifrank.app.springler.model.statics.ExperiencePeriod;
import cz.jirifrank.app.springler.model.statics.LearningAction;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Document(indexName = "experiences")
public class Experience {

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

	@Column(name = "action")
	private LearningAction learningAction;

	@Column(name = "dateTime")
	private LocalDateTime dateTime;

}
