package com.jb.couponProject.Exceptions.CompanyExceptions;

/**
 * a custom exception class for when a company is not found in the database
 */
public class CompanyNotFound extends CompanyException{
    public CompanyNotFound() {
        super("Cannot complete request - a company with that id does not exist");
    }
}
