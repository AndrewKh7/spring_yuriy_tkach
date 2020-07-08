package com.yet.spring.core.spring;

import com.yet.spring.core.beans.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@ComponentScan("com.yet.spring.core")
@PropertySource("classpath:client.properties")
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public Date newDate() {
        return new Date();
    }

    @Bean
    public DateFormat dateFormat(){
        return DateFormat.getDateTimeInstance();
    }

    @Bean
    public Client client(){
        Client client = new Client();
        client.setId(env.getProperty("id"));
        client.setFullName(env.getProperty("FullName"));
        client.setGreeting(env.getProperty("Greeting"));
        return client;
    }

}
