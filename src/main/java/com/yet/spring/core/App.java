package com.yet.spring.core;

import com.yet.spring.core.beans.Event;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.EventLogger;

import java.util.ArrayList;
import java.util.List;

public class App {
    private Client client;
    private EventLogger eventLogger;

    private App(Client client, EventLogger eventlogger) {
        this.eventLogger = eventlogger;
        this.client = client;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) ctx.getBean("app");

        List<Event> evList = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            Event ev = (Event) ctx.getBean("event");
            ev.setMsg("Event message " + i);
            evList.add(ev);
        }

        evList.forEach(app::logEvent);
        ctx.close();
    }

    public void logEvent(Event event) {
        eventLogger.logEvent(event);
    }

}
