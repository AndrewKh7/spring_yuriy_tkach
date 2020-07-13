package com.yet.spring.core.aspects;

import com.yet.spring.core.loggers.EventLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class StatisticsAspects {

    private Map<Class<EventLogger>, Integer> logEventCount = new HashMap<>();

    @Pointcut("execution(* *.logEvent(..))")
    public void allLogEvent(){}

    @AfterReturning( pointcut = "allLogEvent()" )
//    @AfterReturning( pointcut = "execution(* *.logEvent(..))")
    public void logAfterReturning(JoinPoint jp){
        EventLogger logger = (EventLogger) jp.getTarget();
        Class<EventLogger> loggerClass = (Class<EventLogger>) logger.getClass();
        if(this.logEventCount.computeIfPresent(loggerClass,(key,val)->val+1) == null){
            this.logEventCount.put(loggerClass,1);
        }
    }

    public Map<Class<EventLogger>,Integer> getCount(){
        return Collections.unmodifiableMap(this.logEventCount);
    }
}
