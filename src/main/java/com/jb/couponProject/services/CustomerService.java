package com.jb.couponProject.services;

import com.jb.couponProject.Exceptions.CouponExceptions.CouponException;
import com.jb.couponProject.Exceptions.CouponExceptions.CouponNotFound;
import com.jb.couponProject.beans.Category;
import com.jb.couponProject.beans.Coupon;
import com.jb.couponProject.beans.Customer;
import com.jb.couponProject.repos.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 *  the services layer for our customer entities.
 */
@Service
@RequiredArgsConstructor
public class CustomerService extends ClientService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepo.findByUsername(username)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format("Username %s not found",username)));
    }


    public void purchaseCoupon(Coupon coupon,int customerId) throws CouponException {

        Coupon dbCoupon = couponsRepo.findById(coupon.getId()).orElseThrow(CouponNotFound::new);
        if (dbCoupon.getAmount() < 1) {
            throw new CouponException("Cannot purchase coupon - the coupon is out of stock");
        }
        if (dbCoupon.getEndDate().before(new java.sql.Date(System.currentTimeMillis()))) {
            throw new CouponException("Cannot purchase coupon - the coupon is expired");
        }
        Customer saveCustomer = customerRepo.findById(customerId).get();//the customer was confirmed at login - could make a check if he still exists since the login and throw an exception if he doesnt
        if (saveCustomer.getCoupons().contains(dbCoupon)) {
            throw new CouponException("Cannot purchase coupon - only one coupon can be purchased per customer");
        }
        dbCoupon.setAmount(dbCoupon.getAmount() - 1);
        saveCustomer.getCoupons().add(dbCoupon);
        couponsRepo.saveAndFlush(dbCoupon);
        customerRepo.save(saveCustomer);
    }

    public List<Coupon> getCustomerCoupons(int customerId) {
        return customerRepo.findById(customerId).get().getCoupons(); //the customer was confirmed at login - could make a check if he still exists since the login and throw an exception if he doesnt
    }

    public List<Coupon> getCustomerCoupons(Category category,int customerId) {
        return customerRepo.findById(customerId).get().getCoupons().stream().filter(coupon -> coupon.getCategory().equals(category)).collect(Collectors.toList());
    }

    public List<Coupon> getCustomerCoupons(double maxPrice,int customerId) {
        return customerRepo.findById(customerId).get().getCoupons().stream().filter(coupon -> coupon.getPrice() <= maxPrice).collect(Collectors.toList());
    }

    public Customer getCustomerDetails(int customerId) {
        return customerRepo.findById(customerId).get();
    }
    public int findIdByUsername(String username)
    {
        return customerRepo.findIdByUsername(username);
    }
}
