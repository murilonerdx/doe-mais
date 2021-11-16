package com.murilonerdx.doemais.exceptions.handle;

import com.murilonerdx.doemais.exceptions.DataIntegretyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class DefaultErrrorException {
    @ExceptionHandler(value = DataIntegretyException.class)
    public String
    defaultErrorHandler() {
        return "error";
    }
}
