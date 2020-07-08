package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Component
public class FileEventLogger implements EventLogger {
    private File file;

    @Value("${events.file:target/file.txt}")
    private String filename;

    protected FileEventLogger() {}

    public FileEventLogger(String file){
        this.filename = file;
    }

    @PostConstruct
    private void init() {
        this.file = Paths.get(this.filename).toFile();
        if(this.file.exists() && !this.file.canWrite()) {
            throw new IllegalArgumentException(this.file.getPath() + " not found");
        }else if( !this.file.exists()){
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Object obj = new Object();
        obj.hashCode();
    }

    @Override
    public void logEvent(Event event){
        try {
            FileUtils.writeStringToFile(this.file, event.toString()+"\n","UTF-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
