package com.yet.spring.core.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
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
}
