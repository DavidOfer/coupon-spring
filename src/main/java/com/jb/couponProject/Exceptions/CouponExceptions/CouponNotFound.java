package com.jb.couponProject.Exceptions.CouponExceptions;

/**
 * a custom exception class for when a coupon is not found in the database
 */
public class CouponNotFound extends CouponException{
    public CouponNotFound() {
            super("Cannot complete request - a coupon with that id does not exist");

    }
    public CouponNotFound(String message) {
        super(message);
    }
}
