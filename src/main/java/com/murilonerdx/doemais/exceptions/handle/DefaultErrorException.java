package com.murilonerdx.doemais.exceptions.handle;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class GlobalDefaultExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public String
    defaultErrorHandler() {
        return "error";
    }
}
