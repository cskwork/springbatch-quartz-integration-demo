package com.demo.context;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@Import({
        TransactionConfig.class
})
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class AppConfig {

}
