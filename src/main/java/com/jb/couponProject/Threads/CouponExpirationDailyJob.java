package com.jb.couponProject.Threads;

import com.jb.couponProject.repos.CouponsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *a class responsible for preforming a deletion of expired coupons using a scheduled thread
 */
@Component
@Order(1)
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class CouponExpirationDailyJob {
    private final CouponsRepo couponsRepo;
    @Async
  //  @Scheduled(cron = " 0 0 0 * * ?" ,zone="Asia/Jerusalem")
    @Scheduled(fixedRate = 1000*60*24, initialDelay = 1000*5)
    public void eraseCoupons()
    {
        couponsRepo.deleteCouponPurchasesAfter(new java.sql.Date(System.currentTimeMillis()));
        couponsRepo.deleteByEndDateBefore(new java.sql.Date(System.currentTimeMillis()));
        System.out.println("expired coupon thread activated");
    }
}
