package com.yet.spring.core.beans;

import java.util.ArrayList;
import java.util.List;

public class CacheEventLogger extends FileEventLogger {
    private int cacheSize;
    private List<Event> cache;

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

    private void destroy(){
        if(!this.cache.isEmpty()){
            writeCacheToFile();
            this.cache.clear();
        }
    }
}
