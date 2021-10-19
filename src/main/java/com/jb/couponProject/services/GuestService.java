package com.jb.couponProject.services;

import com.jb.couponProject.Exceptions.CouponExceptions.CouponNotFound;
import com.jb.couponProject.beans.Coupon;
import com.jb.couponProject.repos.CouponsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *  the services layer for our non authenticated requests.
 */
@Service
public class GuestService {
    @Autowired
    protected CouponsRepo couponsRepo;

    public List<Coupon> getAllCoupons(){
        return couponsRepo.findAll();
    }

    public Coupon getOneCoupon(int couponId) throws CouponNotFound {
        Optional<Coupon> coupon = couponsRepo.findById(couponId);
        return couponsRepo.findById(couponId).orElseThrow(CouponNotFound::new);
    }
}
