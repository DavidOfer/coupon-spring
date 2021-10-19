package com.jb.couponProject.repos;

import com.jb.couponProject.beans.Category;
import com.jb.couponProject.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface CouponsRepo extends JpaRepository<Coupon,Integer> {
    Optional<Coupon> findByIdAndCompanyId(Integer integer, int companyId);
    List<Coupon> findByCompanyId(int companyId);
    List<Coupon> findByCompanyIdAndCategory(int companyId, Category category);
    List<Coupon> findByCompanyIdAndPriceLessThanEqual(int companyId,double maxPrice);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM customers_vs_coupons WHERE coupon_id=:id",nativeQuery = true)
    void deleteCouponPurchasesByCouponId(int id);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM customers_vs_coupons WHERE customer_id=:id",nativeQuery = true)
    void deleteCouponPurchasesByCustomerId(int id);
    @Transactional
    void deleteByEndDateBefore(Date date);
    @Modifying
    @Transactional
    @Query(value="DELETE FROM customers_vs_coupons cc WHERE EXISTS(SELECT * FROM coupon co WHERE cc.coupon_id=co.id AND co.end_date < :date)", nativeQuery = true)
    void deleteCouponPurchasesAfter(Date date);
    boolean existsByTitleAndCompanyId(String name,int companyId);

    @Query(value = "SELECT c.company_id FROM coupon c WHERE c.id=:id",nativeQuery = true)
    int findCompanyIdById(int id);
}
