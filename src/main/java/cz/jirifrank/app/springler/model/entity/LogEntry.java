package cz.jirifrank.app.springler.model.entity;

import cz.jirifrank.app.springler.model.statics.Action;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "action")
    private Action action;

    @Column(name = "value")
    private String value;

    @Column(name = "dateTime")
    private LocalDateTime dateTime;

}