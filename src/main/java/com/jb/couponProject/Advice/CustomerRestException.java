package com.jb.couponProject.Advice;


import com.jb.couponProject.Exceptions.CustomerExceptions.CustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * customer exception advice class for handling customer exceptions
 */
@RestController
@ControllerAdvice
public class CustomerRestException {
    @ExceptionHandler(value={CustomerException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleException(Exception e){
        return new ErrorDetail("Customer Error", e.getMessage());
    }
}
