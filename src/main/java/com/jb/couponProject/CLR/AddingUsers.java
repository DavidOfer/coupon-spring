package com.jb.couponProject.CLR;

import com.jb.couponProject.beans.*;
import com.jb.couponProject.services.AdminService;
import com.jb.couponProject.services.CompanyService;
import com.jb.couponProject.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * mock data component
 */

@Component
@RequiredArgsConstructor
public class AddingUsers implements CommandLineRunner {
    private final AdminService adminService;
    private final CustomerService customerService;
    private final CompanyService companyService;

    @Override
    public void run(String... args) throws Exception {
        try{
        adminService.addAdmin(new Admin(0,
                "admin@admin.com",
                "12345",
                "ROLE_ADMIN",
                true,
                true,
                true,
                true
        ));
        adminService.addAdmin(new Admin(0,
                "client@mail.com",
                "12345",
                "ROLE_ADMIN",
                true,
                true,
                true,
                true
        ));
        System.out.println("added users in CLR");

        adminService.addCustomer(new Customer(0,
                "poomba",
                "balumba",
                "poomba@mail.com",
                "12345",
                "ROLE_CUSTOMER",
                null,
                null,
                true,
                true,
                true,
                true
        ));
        System.out.println("added customers in CLR");
        adminService.addCompany(new Company(0,
                "osem",
                "osem@mail.com",
                "12345",
                "ROLE_COMPANY",
                null,
        null,
                true,
                true,
                true,
                true
        ));
        adminService.addCompany(new Company(0,
                "Telma",
                "telma@mail.com",
                "12345",
                "ROLE_COMPANY",
                null,
                null,
                true,
                true,
                true,
                true
        ));
        adminService.addCompany(new Company(0,
                "Nestle",
                "nestle@mail.com",
                "12345",
                "ROLE_COMPANY",
                null,
                null,
                true,
                true,
                true,
                true
        ));

        companyService.addCoupon(Coupon.builder().id(0).companyId(1).category(Category.FOOD).title("bamba")
                .description("lots of bamba").startDate(Date.valueOf("2022-11-09")).endDate(Date.valueOf("2023-11-09"))
                .amount(250).price(100).image("https://upload.wikimedia.org/wikipedia/commons/1/11/Bamba_snack.jpg").customers(null).build(),1);
        companyService.addCoupon(Coupon.builder().id(0).companyId(1).category(Category.FOOD).title("Bisli")
                .description("lots of bisli").startDate(Date.valueOf("2022-11-09")).endDate(Date.valueOf("2023-01-05"))
                .amount(500).price(200).image("https://osemcat.signature-it.com/images/Fittings/osem-hq/Upload_Pictures/Prod_Pic/6929507/Catalog/6929507_7290000467511_L_Enlarge.jpg").customers(null).build(),1);
        companyService.addCoupon(Coupon.builder().id(0).companyId(2).category(Category.FOOD).title("POTATOES")
                .description("lots of bamba").startDate(Date.valueOf("2022-11-10")).endDate(Date.valueOf("2023-12-03"))
                .amount(5).price(50).image("https://images.heb.com/is/image/HEBGrocery/000318982").customers(null).build(),2);
        companyService.addCoupon(Coupon.builder().id(0).companyId(2).category(Category.VACATION).title("Maldib Vacation")
                .description("a very long vacation").startDate(Date.valueOf("2022-07-09")).endDate(Date.valueOf("2023-11-11"))
                .amount(5).price(300).image("https://www.rd.com/wp-content/uploads/2020/01/GettyImages-1131335393-e1580493890249-scaled.jpg").customers(null).build(),2);
        companyService.addCoupon(Coupon.builder().id(0).companyId(2).category(Category.FOOD).title("Choclate")
                .description("very good choclate").startDate(Date.valueOf("2022-08-09")).endDate(Date.valueOf("2025-11-09"))
                .amount(5).price(400).image("https://upload.wikimedia.org/wikipedia/commons/7/70/Chocolate_%28blue_background%29.jpg").customers(null).build(),3);
        companyService.addCoupon(Coupon.builder().id(0).companyId(2).category(Category.ELECTRICITY).title("Choclate Machine")
                .description("big choaclate machine").startDate(Date.valueOf("2022-12-09")).endDate(Date.valueOf("2024-11-09"))
                .amount(5).price(50).image("https://5.imimg.com/data5/TK/IL/MY-1992626/chocolate-fountain-500x500.jpg").customers(null).build(),3);
        }
        catch (Exception e)
        {
            System.out.println("clr was already run");
        }
    }
}
