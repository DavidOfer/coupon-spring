package com.jb.couponProject.Exceptions.CustomerExceptions;

/**
 * a custom exception class for customer related exceptions
 */
public class CustomerException extends Exception{
    public CustomerException() {
    }

    public CustomerException(String message) {
        super(message);
    }
}
