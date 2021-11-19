package com.murilonerdx.doemais.exceptions.handle;

import com.murilonerdx.doemais.exceptions.DataIntegretyException;
import com.murilonerdx.doemais.exceptions.StandardError;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class DefaultErrrorException {
    @ExceptionHandler(value = RuntimeException.class)
    public String
    defaultErrorHandler(Model model, ObjectNotFoundException e, HttpServletRequest request)
    {
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Integridade de dados", e.getMessage(), request.getRequestURI());
        model.addAttribute("error", err.getError());
        return "error";
    }
}