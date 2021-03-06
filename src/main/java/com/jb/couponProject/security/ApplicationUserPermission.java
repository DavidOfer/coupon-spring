package com.jb.couponProject.security;

/**
 * a user permission enum class
 */
public enum ApplicationUserPermission {
    ADMIN_READ("administrator:read"),
    ADMIN_WRITE("administrator:write"),
    COMPANY_READ("company:read"),
    COMPANY_WRITE("company:write"),
    CUSTOMER_READ("client:read"),
    CUSTOMER_WRITE("client:write"),
    COUPON_READ("coupon:read"),
    COUPON_WRITE("coupon:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }

}
