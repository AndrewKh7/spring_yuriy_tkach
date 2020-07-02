package com.yet.spring.core;

import com.yet.spring.core.beans.Event;
import com.yet.spring.core.beans.EventType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.EventLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {
    private Client client;
    private EventLogger defaultLogger;
    private Map<EventType,EventLogger> loggers;

    private App(Client client, EventLogger eventlogger, Map<EventType,EventLogger> loggerMap) {
        this.defaultLogger = eventlogger;
        this.client = client;
        this.loggers = loggerMap;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) ctx.getBean("app");

        Event ev1 = (Event) ctx.getBean("event");
        ev1.setMsg("some Message!");

        Event ev2 = (Event) ctx.getBean("event");
        ev2.setMsg("Error Message!");

        Event ev3 = (Event) ctx.getBean("event");
        ev3.setMsg("Info Message!");

        app.logEvent(ev1);
        app.logEvent(ev2, EventType.ERROR);
        app.logEvent(ev3,EventType.INFO);
        ctx.close();
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
