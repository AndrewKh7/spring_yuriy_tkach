package com.yet.spring.core.spring;

import com.yet.spring.core.beans.EventType;
import com.yet.spring.core.loggers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.Resource;
import java.util.*;

@Configuration
@ComponentScan("com.yet.spring.core.loggers")
public class LoggerConfig {

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertyConfigIn(){
//        return new PropertySourcesPlaceholderConfigurer();
//    }

    @Resource(name = "consoleEventLogger")
    private EventLogger consoleEventLogger;

    @Resource(name = "fileEventLogger")
    private EventLogger fileEventLogger;

    @Resource(name = "cacheEventLogger")
    private EventLogger cacheEventLogger;

    @Resource(name = "combinedEventLogger")
    private EventLogger combinedEventLogger;

    @Bean
    public Collection<EventLogger> combinedLoggers(){
        Collection<EventLogger> loggers = new ArrayList<>();
        loggers.add(this.consoleEventLogger);
        loggers.add(fileEventLogger);
        return loggers;
    }

    @Bean
    public Map<EventType, EventLogger> loggerMap(){
        Map<EventType, EventLogger> map = new EnumMap<EventType, EventLogger>(EventType.class);
        map.put(EventType.INFO, this.consoleEventLogger);
        map.put(EventType.ERROR, this.combinedEventLogger);
        return map;
    }

    @Bean
    public EventLogger defaultEventLogger(){
        return this.cacheEventLogger;
    }
}
