package cz.jirifrank.app.springler.model.dto;

import cz.jirifrank.app.springler.model.statics.Action;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Log {
    private Action action;
    private String value;
    private LocalDateTime dateTime;
}
