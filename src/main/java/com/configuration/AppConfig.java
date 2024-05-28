package com.configuration;

import org.springframework.context.annotation.*;

@Configuration
@Import({
        TransactionConfig.class
})
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class AppConfig {

}
