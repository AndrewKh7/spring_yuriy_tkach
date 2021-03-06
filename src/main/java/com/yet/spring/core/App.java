package com.yet.spring.core;

import com.yet.spring.core.aspects.StatisticsAspects;
import com.yet.spring.core.beans.Event;
import com.yet.spring.core.beans.EventType;
import com.yet.spring.core.loggers.ConsoleEventLogger;
import com.yet.spring.core.loggers.EventLogger;
import com.yet.spring.core.loggers.FileEventLogger;
import com.yet.spring.core.spring.AppConfig;
import com.yet.spring.core.spring.LoggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.yet.spring.core.beans.Client;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class App {

    @Autowired
    private Client client;
    @Value("#{T(com.yet.spring.core.beans.Event).isDay() ? fileEventLogger : consoleEventLogger}")
    private EventLogger defaultLogger;
    @Resource(name = "loggerMap")
    private Map<EventType,EventLogger> loggers;

    private App(){}

    private App(Client client, EventLogger eventlogger, Map<EventType,EventLogger> loggerMap) {
        this.defaultLogger = eventlogger;
        this.client = client;
        this.loggers = loggerMap;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class, LoggerConfig.class);
        App app = (App) ctx.getBean("app");
        Event ev1 = (Event) ctx.getBean("event");
        ev1.setMsg("some Message!");

        Event ev2 = (Event) ctx.getBean("event");
        ev2.setMsg("Error Message!");

        Event ev3 = (Event) ctx.getBean("event");
        ev3.setMsg("Info Message! " + app.client.getId()+" "+app.client.getGreeting());


        app.appLogEvent(ev1);
        app.appLogEvent(ev2, EventType.ERROR);
        app.appLogEvent(ev3,EventType.INFO);

        app.printLoggerStatistics((StatisticsAspects) ctx.getBean("statisticsAspects"));
        ctx.close();

    }

    public void appLogEvent(Event event){
        this.defaultLogger.logEvent(event);
    }

    public void appLogEvent(Event event, EventType type) {
        EventLogger logger = loggers.get(type);
        if(logger == null){
            logger = this.defaultLogger;
        }
        logger.logEvent(event);
    }

    public void printLoggerStatistics(StatisticsAspects statistic){
        Map<Class<EventLogger>, Integer>  map = statistic.getCount();
        System.out.println("Logger statistic: ");
        map.forEach((key,val) -> System.out.println(key.getSimpleName() + ": " + val));
    }
}
