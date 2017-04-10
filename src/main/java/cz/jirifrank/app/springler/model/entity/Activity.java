package cz.jirifrank.app.springler.model.entity;

import cz.jirifrank.app.springler.model.statics.ActivityResult;
import cz.jirifrank.app.springler.model.statics.ActivitySeverity;
import cz.jirifrank.app.springler.model.statics.ActivityType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private ActivityType type;

	@Column(name = "severity")
	@Enumerated(EnumType.STRING)
	private ActivitySeverity severity;

	@Column(name = "result")
	@Enumerated(EnumType.STRING)
	private ActivityResult result;

	@Column(name = "dateTime")
	private LocalDateTime dateTime;

}
