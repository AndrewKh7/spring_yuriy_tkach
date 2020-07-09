package com.yet.spring.core.beans;

import org.springframework.beans.factory.annotation.Value;

public class Client {
    private String id;
    private String fullName;
    @Value("#{ systemEnvironment['USER']}")
    private String greeting;

    public Client(){}

    public Client(String id, String fullName){
        this.id = id;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }
}
