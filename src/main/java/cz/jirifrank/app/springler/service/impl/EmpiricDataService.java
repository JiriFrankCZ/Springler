package cz.jirifrank.app.springler.service.impl;

import cz.jirifrank.app.springler.model.dto.HumidityMeasurement;
import cz.jirifrank.app.springler.model.dto.Log;
import cz.jirifrank.app.springler.model.entity.Humidity;
import cz.jirifrank.app.springler.model.entity.LogEntry;
import cz.jirifrank.app.springler.model.statics.Action;
import cz.jirifrank.app.springler.repository.HumidityRepository;
import cz.jirifrank.app.springler.repository.LogEntryRepository;
import cz.jirifrank.app.springler.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmpiricDataService implements DataService {

    @Autowired
    private HumidityRepository humidityRepository;

    @Autowired
    private LogEntryRepository logEntryRepository;

    @Override
    @Transactional
    public void persistHumidity(Double soilMoistureHumidity) {
        Humidity humidity = new Humidity();
        humidity.setValue(soilMoistureHumidity);
        humidity.setDateTime(LocalDateTime.now());

        humidityRepository.save(humidity);
    }

    @Override
    public void persistLog(Action action, String value) {
        LogEntry logEntry = new LogEntry();
        logEntry.setAction(action);
        logEntry.setValue(value);
        logEntry.setDateTime(LocalDateTime.now());

        logEntryRepository.save(logEntry);
    }

    @Override
    public List<HumidityMeasurement> getLatest() {
        Page<Humidity> humidityPage = humidityRepository.findAll(new PageRequest(0, 100, new Sort(Sort.Direction.DESC, "id")));

        return humidityPage
                .getContent()
                .stream()
                .map(humidity -> {
                            HumidityMeasurement humidityMeasurement = new HumidityMeasurement();
                            humidityMeasurement.setValue(humidity.getValue());
                            humidityMeasurement.setDateTime(humidity.getDateTime());
                            return humidityMeasurement;
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public HumidityMeasurement getLast() {
        Humidity humidity = humidityRepository.findFirstByOrderByDateTimeDesc();
        HumidityMeasurement humidityMeasurement = new HumidityMeasurement();

        if (humidity != null) {
            humidityMeasurement.setValue(humidity.getValue());
            humidityMeasurement.setDateTime(humidity.getDateTime());
        }

        return humidityMeasurement;
    }

    @Override
    public List<Log> getLatestLogs() {
        return this.searchLogs(100);
    }

    @Override
    public List<Log> getLogs(Integer size) {
        return this.searchLogs(size);
    }

    private List<Log> searchLogs(Integer size){
        Page<LogEntry> logEntryPage = logEntryRepository.findAll(new PageRequest(0, 200, new Sort(Sort.Direction.DESC, "id")));

        return logEntryPage
                .getContent()
                .stream()
                .map(logEntry -> {
                            Log log = new Log();
                            log.setAction(logEntry.getAction());
                            log.setValue(logEntry.getValue());
                            log.setDateTime(logEntry.getDateTime());
                            return log;
                        }
                ).collect(Collectors.toList());
    }
}