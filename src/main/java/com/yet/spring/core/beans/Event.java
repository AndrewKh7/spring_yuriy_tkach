package com.yet.spring.core.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@Component
@Scope("prototype")
public class Event {

    private int id;
    private String msg;
    @Autowired
    @Qualifier("newDate")
    private Date date;
    @Autowired
    private DateFormat df;
    private static LocalTime morning;
    private static LocalTime evening;

    static {
        try(FileInputStream propFile = new FileInputStream("events.properties") ){
            Properties prop = new Properties();
            prop.load(propFile);
            String mor = prop.getProperty("morning_time");
            String evn = prop.getProperty("evening_time");
            Event.morning = LocalTime.parse( mor!=null ? mor : "08:00:00");
            Event.evening = LocalTime.parse( evn!=null ? evn : "17:00:00");
        }catch(IOException e){
            System.out.println("morning and evening time set in default value.");
            Event.morning = LocalTime.parse("08:00:00");
            Event.evening = LocalTime.parse("17:00:00");
        }
    }

    public Event(Date date, DateFormat df){
        Random random = new Random();
        this.id = random.nextInt(899)+100;
        this.date = date;
        this.df = df;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", date=" + df.format(this.date) +
                '}';
    }

    public static boolean isDay(){
        LocalTime time = LocalTime.now();

        if(time.isAfter(Event.morning) && time.isBefore(Event.evening))
            return true;
        else
            return false;
    }
}
