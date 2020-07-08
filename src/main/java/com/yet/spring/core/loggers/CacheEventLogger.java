package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class CacheEventLogger extends FileEventLogger {
    @Value("${cache.size:5}")
    private int cacheSize;
    private List<Event> cache;

    private CacheEventLogger(){
    }

    public CacheEventLogger(int cacheSize, String file) {
        super(file);
        this.cacheSize = cacheSize;
        this.cache = new ArrayList<>();
    }

    @Override
    public void logEvent(Event event) {
        this.cache.add(event);
        if (this.cache.size() >= this.cacheSize) {
            writeCacheToFile();
            this.cache.clear();
        }
    }

    private void writeCacheToFile() {
        this.cache.forEach(super::logEvent);
   }

    @PostConstruct
    private void init(){
        this.cache = new ArrayList<>();
    }

    @PreDestroy
    private void destroy(){
        if(!this.cache.isEmpty()){
            writeCacheToFile();
            this.cache.clear();
        }
    }
}
