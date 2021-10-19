package com.jb.couponProject.controllers;


import com.jb.couponProject.Exceptions.CouponExceptions.CouponException;
import com.jb.couponProject.JWT.JWTUtil;
import com.jb.couponProject.beans.Category;
import com.jb.couponProject.beans.Coupon;
import com.jb.couponProject.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;


/**
 * a REST api controller for exposing our customer user end points
 */

@RestController
@RequestMapping("/customer")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class CustomerController {
    private final CustomerService customerService;
    private final JWTUtil jwtUtil;


    @PostMapping("/purchaseCoupon")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws CouponException {
        try {
            customerService.purchaseCoupon(coupon, jwtUtil.getId(token));
        } catch (CouponException e) {
            throw new CouponException(e.getMessage());
        }
        return ResponseEntity.ok().body("coupon purchased");
    }
    @PostMapping("getCustomerDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token)
    {
        return ResponseEntity.ok(customerService.getCustomerDetails(jwtUtil.getId(token)));
    }

    @PostMapping("getCustomerCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token)
    {
        return ResponseEntity.ok(customerService.getCustomerCoupons(jwtUtil.getId(token)));
    }

    @PostMapping("getCouponsByCat/{category}")
    public ResponseEntity<?> getCouponsByCat(@RequestHeader(name = "Authorization") String token, @PathVariable Category category)
    {
        return ResponseEntity.ok(customerService.getCustomerCoupons(category,jwtUtil.getId(token)));
    }
    @PostMapping("getCouponsByPrice/{maxPrice}")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token,@PathVariable double maxPrice)
    {
        return ResponseEntity.ok(customerService.getCustomerCoupons(maxPrice,jwtUtil.getId(token)));
    }


}
