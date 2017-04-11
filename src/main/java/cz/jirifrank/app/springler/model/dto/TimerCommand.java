package cz.jirifrank.app.springler.model.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

/**
 * Created by FrankJ on 10.4.2017.
 */
@Data
public class TimerCommand {
	private List<LocalTime> timeList;
}
