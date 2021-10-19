package com.jb.couponProject.controllers;



import com.jb.couponProject.Exceptions.CouponExceptions.CouponException;

import com.jb.couponProject.JWT.JWTUtil;

import com.jb.couponProject.beans.Category;

import com.jb.couponProject.beans.Coupon;

import com.jb.couponProject.services.CompanyService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


/**
 * a REST api controller for exposing our company user end points
 */
@RestController
@RequestMapping("/company")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_COMPANY')")
public class CompanyController {
    private final CompanyService companyService;
    private final JWTUtil jwtUtil;

    @PostMapping ("/addCoupon")
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token,@RequestBody Coupon coupon) throws CouponException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/addUser").toUriString());
        try {
            return ResponseEntity.created(uri).body(companyService.addCoupon(coupon,jwtUtil.getId(token)));
        } catch (CouponException e) {
            throw new CouponException(e.getMessage());
        }
    }

    @PutMapping("updateCoupon")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token,@RequestBody Coupon coupon) throws CouponException {
        try {
            companyService.updateCoupon(coupon,jwtUtil.getId(token));
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (CouponException e) {
            System.out.println(e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }
    @PostMapping("deleteCoupon")
    public ResponseEntity<?> deleteCoupon(@RequestBody int couponId,@RequestHeader(name = "Authorization") String token) throws CouponException {
        try{
            companyService.deleteCoupon(couponId,jwtUtil.getId(token));
            return ResponseEntity.ok().build();
        }
        catch (CouponException e)
        {
            throw new CouponException(e.getMessage());
        }
    }

    @PostMapping("getCoupons")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token)
    {
        return ResponseEntity.ok(companyService.getCompanyCoupons(jwtUtil.getId(token)));
    }

    @PostMapping("getCouponsByCat/{category}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token,@PathVariable Category category)
    {
        return ResponseEntity.ok(companyService.getCompanyCoupons(category,jwtUtil.getId(token)));
    }
    @PostMapping("getCouponsByPrice")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token,@RequestBody double maxPrice)
    {
        return ResponseEntity.ok(companyService.getCompanyCoupons(maxPrice,jwtUtil.getId(token)));
    }
    @PostMapping("getCompanyDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token)
    {
        return ResponseEntity.ok(companyService.getCompanyDetails(jwtUtil.getId(token)));
    }
}