package com.jb.couponProject.services;

import com.jb.couponProject.Exceptions.CouponExceptions.CouponException;
import com.jb.couponProject.Exceptions.CouponExceptions.CouponNotFound;
import com.jb.couponProject.beans.Category;
import com.jb.couponProject.beans.Company;
import com.jb.couponProject.beans.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  the services layer for our company entities.
 */
@RequiredArgsConstructor
@Service
public class CompanyService extends ClientService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return companyRepo.findByUsername(username)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format("Username %s not found",username)));
    }


    public int addCoupon(Coupon coupon,int companyId) throws CouponException {
        coupon.setCompanyId(companyId);
        if(couponsRepo.existsByTitleAndCompanyId(coupon.getTitle(),companyId))
        {
            throw new CouponException("Cannot add coupon - a coupon with that title already exists for this company");
        }
        return couponsRepo.save(coupon).getId();
    }

    public void updateCoupon(Coupon coupon,int companyId) throws CouponNotFound
    {
        Coupon coupon1 = couponsRepo.findByIdAndCompanyId(coupon.getId(),companyId).orElseThrow(CouponNotFound::new);
        coupon1.setCategory(coupon.getCategory());
        coupon1.setTitle(coupon.getTitle());
        coupon1.setDescription(coupon.getDescription());
        coupon1.setStartDate(coupon.getStartDate());
        coupon1.setEndDate(coupon.getEndDate());
        coupon1.setAmount(coupon.getAmount());
        coupon1.setPrice(coupon.getPrice());
        coupon1.setImage(coupon.getImage());
        couponsRepo.saveAndFlush(coupon1);
    }
    public void deleteCoupon(int couponId,int companyId) throws CouponException {
        //this is a check that a company delete its own coupon
        if(couponsRepo.findCompanyIdById(couponId)== companyId)
        {
        couponsRepo.deleteCouponPurchasesByCouponId(couponId);
        couponsRepo.deleteById(couponId);
        }
        else{
            throw new CouponException("The coupon you are trying to delete does not belong to this company");
        }
    }
    public List<Coupon> getCompanyCoupons(int companyId)
    {
        return couponsRepo.findByCompanyId(companyId);
    }
    public List<Coupon> getCompanyCoupons(Category category,int companyId)
    {
        return couponsRepo.findByCompanyIdAndCategory(companyId,category);
    }
    public List<Coupon> getCompanyCoupons(double maxPrice,int companyId)
    {
        return couponsRepo.findByCompanyIdAndPriceLessThanEqual(companyId,maxPrice);
    }
    public Company getCompanyDetails(int companyId)
    {
        return companyRepo.findById(companyId).orElse(null);
    }
    public int findIdByUsername(String username)
    {
        return companyRepo.findIdByUsername(username);
    }
}
