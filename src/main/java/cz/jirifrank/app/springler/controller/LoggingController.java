package cz.jirifrank.app.springler.controller;

import cz.jirifrank.app.springler.model.dto.Log;
import cz.jirifrank.app.springler.model.statics.Action;
import cz.jirifrank.app.springler.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Data collection controller for storing all the incomming data
 *
 * @author Jiří Frank
 */
@RestController
@Slf4j
public class LoggingController {

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/logging/{action}/{action}", method = RequestMethod.POST)
    public void uploadData(@PathVariable("action") Action action, @PathVariable("value") String value) {
        log.info("New logging data [{},{}].", action, value);
        dataService.persistLog(action, value);
        log.info("Log has been saved.");
    }

    @RequestMapping(value = "/logging/latest", method = RequestMethod.GET)
    public List<Log> getLatest(){
        log.info("Latest logs requeted.");
        return dataService.getLatestLogs();
    }
}