package cz.jirifrank.app.springler.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Humidity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "value")
	private Double value;

	@Column(name = "dateTime", columnDefinition = "timestamp with time zone not null")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
}
