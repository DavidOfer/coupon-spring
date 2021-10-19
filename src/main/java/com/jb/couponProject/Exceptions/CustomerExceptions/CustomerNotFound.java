package com.jb.couponProject.Exceptions.CustomerExceptions;

/**
 * a custom exception class for when a customer is not found in the database
 */
public class CustomerNotFound extends CustomerException{
    public CustomerNotFound() {
        super("Cannot complete request - a customer with that id does not exist");
    }
}
