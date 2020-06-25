package com.yet.spring.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.EventLogger;

public class App {
    private Client client;
    private EventLogger eventLogger;

    private App(Client client, EventLogger eventlogger){
        this.eventLogger = eventlogger;
        this.client = client;
    }

    public static void main(String[] args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) ctx.getBean("app");

        app.logEvent("Some event for user 1");
//        app.logEvent("Some event for user 2");
    }

    public void logEvent(String msg){
        String message = msg.replaceAll(
                client.getId(), client.getFullName());
        eventLogger.logEvent(message);
    }

}
