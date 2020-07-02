package com.yet.spring.core.beans;

import java.util.ArrayList;
import java.util.List;

public class CombinedEventLogger implements EventLogger {
    private List<EventLogger> loggers;

    public CombinedEventLogger(List<EventLogger> loggers){
        this.loggers = loggers;
    }

    @Override
    public void logEvent(Event event){
        this.loggers.forEach(l -> l.logEvent(event));
    }
}
