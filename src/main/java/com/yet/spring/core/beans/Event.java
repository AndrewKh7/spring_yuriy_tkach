package com.yet.spring.core.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.time.format.DateTimeFormatter;

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
//    @Value("#{ T(java.time.LocalTime).parse('${events.morning_time:8:0:0}', T(java.time.format.DateTimeFormatter).ofPattern('HH:mm:ss'))}")
    @Value("#{new java.text.SimpleDateFormat('HH:mm:ss').parse('${events.morning_time:8:0:0}')}")
    private LocalTime morning;
//    @Value("#{ T(java.time.LocalTime).parse('${events.morning_time:17:0:0}',T(java.time.format.DateTimeFormatter).ofPattern('HH:mm:ss'))}")
    private LocalTime evening;

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
        LocalTime t = LocalTime.parse("9:0:0",DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(t);
        System.out.println(time);

        return false;
    }
}
