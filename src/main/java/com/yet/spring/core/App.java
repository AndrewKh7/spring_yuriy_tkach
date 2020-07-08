package com.yet.spring.core;

import com.yet.spring.core.beans.Event;
import com.yet.spring.core.beans.EventType;
import com.yet.spring.core.loggers.EventLogger;
import com.yet.spring.core.spring.AppConfig;
import com.yet.spring.core.spring.LoggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.yet.spring.core.beans.Client;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class App {

    @Autowired
    private Client client;
    @Resource(name = "defaultEventLogger")
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
//        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class, LoggerConfig.class);
        App app = (App) ctx.getBean("app");
        Event ev1 = (Event) ctx.getBean("event");
        ev1.setMsg("some Message!");

        Event ev2 = (Event) ctx.getBean("event");
        ev2.setMsg("Error Message!");

        Event ev3 = (Event) ctx.getBean("event");
        ev3.setMsg("Info Message!");

        Event.isDay();

        app.logEvent(ev1);
        app.logEvent(ev2, EventType.ERROR);
        app.logEvent(ev3,EventType.INFO);
        ctx.close();

        String str = "Hello world!";
        char[] arr = str.toCharArray();
    }

    public void logEvent(Event event){
        this.defaultLogger.logEvent(event);
    }
    public void logEvent(Event event, EventType type) {
        EventLogger logger = loggers.get(type);
        if(logger == null){
            logger = this.defaultLogger;
        }
        logger.logEvent(event);
    }

}
