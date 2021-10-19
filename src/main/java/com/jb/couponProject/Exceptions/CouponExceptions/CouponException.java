package com.jb.couponProject.Exceptions.CouponExceptions;

/**
 * a custom exception class for coupon related exceptions
 */
public class CouponException extends Exception{
    public CouponException() {
    }

    public CouponException(String message) {
        super(message);
    }
}
