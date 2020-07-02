package com.yet.spring.core.beans;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class FileEventLogger implements EventLogger{
    private File file;
    private String filename;

    public FileEventLogger(String file){
        this.file = Paths.get(file).toFile();

    }

    private void init() {
        if(!this.file.exists() && !this.file.canWrite()) {
            throw new IllegalArgumentException(this.file.getPath() + " not found");
        }else if( !this.file.exists()){
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
