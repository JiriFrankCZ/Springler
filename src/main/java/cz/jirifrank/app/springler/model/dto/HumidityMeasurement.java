package cz.jirifrank.app.springler.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import java.util.Date;


@Data
public class HumidityMeasurement {

	@Min(0)
	private Double value;

	private Date dateTime;

}
