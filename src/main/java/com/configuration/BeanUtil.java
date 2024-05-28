package com.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BeanUtil {
    @Autowired
    private ApplicationContext applicationContext;

    public Object getBean(String name){
        return applicationContext.getBean(name);
    }

}