package com.jb.couponProject.Advice;


import com.jb.couponProject.Exceptions.CompanyExceptions.CompanyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Admin exception advice class for handling admin exceptions
 */

@RestController
@ControllerAdvice
public class AdminRestException {
    @ExceptionHandler(value={CompanyException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleException(Exception e){
        return new ErrorDetail("company Error", e.getMessage());
    }
}
