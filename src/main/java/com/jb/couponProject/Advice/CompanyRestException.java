package com.jb.couponProject.Advice;


import com.jb.couponProject.Exceptions.CouponExceptions.CouponException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * company exception advice class for handling company exceptions
 */

@RestController
@ControllerAdvice
public class CompanyRestException {
    @ExceptionHandler(value={CouponException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleException(Exception e){
        return new ErrorDetail("company Error", e.getMessage());
    }
}
