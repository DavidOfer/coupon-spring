package com.jb.couponProject.controllers;


import com.jb.couponProject.Exceptions.CouponExceptions.CouponException;
import com.jb.couponProject.Exceptions.CouponExceptions.CouponNotFound;
import com.jb.couponProject.services.GuestService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;


/**
 * a REST api controller for exposing our non token-verified end points
 */
@RestController
@RequestMapping("/guest")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class GuestController {
    private final GuestService guestService;

    @GetMapping("getall")
    public ResponseEntity<?> getAllCoupons(){
        return new ResponseEntity<>(guestService.getAllCoupons(),HttpStatus.OK);
    }
    @GetMapping("getone/{couponId}")
    public ResponseEntity<?> getAllCoupons(@PathVariable int couponId) throws CouponNotFound {
        try{
            return new ResponseEntity<>(guestService.getOneCoupon(couponId),HttpStatus.OK);

        }
        catch (CouponException e)
        {
            throw new CouponNotFound(e.getMessage());
        }
    }
}
