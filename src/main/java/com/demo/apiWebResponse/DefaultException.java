package com.demo.apiWebResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

public class DefaultException extends RuntimeException{
    public DefaultException(String message) {
        super(message);
    }
}
