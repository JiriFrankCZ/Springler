package cz.jirifrank.app.springler.controller;

import cz.jirifrank.app.springler.model.dto.Log;
import cz.jirifrank.app.springler.model.statics.Action;
import cz.jirifrank.app.springler.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Data collection controller for storing all the incomming data
 *
 * @author Jiří Frank
 */0
@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:9090")
public class LoggingController {

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/logging/{action}/{value}", method = RequestMethod.POST)
    public void uploadData(@PathVariable("action") Action action, @PathVariable("value") String value) {
        log.info("New logging data [{},{}].", action, value);
        dataService.persistLog(action, value);
        log.info("Log has been saved.");
    }

    @RequestMapping(value = "/logging/latest", method = RequestMethod.GET)
    public List<Log> getLatest(){
        log.info("Latest logs requested.");
        return dataService.getLatestLogs();
    }

    @RequestMapping(value = "/logging/{size}", method = RequestMethod.GET)
    public List<Log> getGivenSize(@PathVariable("size") Integer size){
        log.info("Logs requested for size {}.", size);
        return dataService.getLogs(size);
    }
}