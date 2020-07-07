package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CombinedEventLogger implements EventLogger {

    @Resource(name = "combinedLoggers")
    private List<EventLogger> loggers;

    public CombinedEventLogger(List<EventLogger> loggers){
        this.loggers = loggers;
    }

    @Override
    public void logEvent(Event event){
        this.loggers.forEach(l -> l.logEvent(event));
    }
}
