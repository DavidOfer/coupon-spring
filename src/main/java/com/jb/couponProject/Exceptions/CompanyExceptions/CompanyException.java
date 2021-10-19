package com.jb.couponProject.Exceptions.CompanyExceptions;

/**
 * a custom exception class for company related exceptions
 */
public class CompanyException extends Exception{
    public CompanyException() {
    }

    public CompanyException(String message) {
        super(message);
    }
}
