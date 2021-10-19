package com.jb.couponProject.services;

import com.jb.couponProject.repos.CompanyRepo;
import com.jb.couponProject.repos.CouponsRepo;
import com.jb.couponProject.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is an abstract class of all the facades in the system.
 * <p>
 * This class has 3 attributes of dao's to give us access to the methods in the DBDAO'S.
 */
public abstract class ClientService {
    @Autowired
    protected CompanyRepo companyRepo;
    @Autowired
    protected CustomerRepo customerRepo;
    @Autowired
    protected CouponsRepo couponsRepo;

    /**
     * This is abstact method we will implements in the child classes .
     * @param email email of the user that try to log in
     * @param password the password of the user that try to log in
     * @return methods that override this method return true if the credentials are correct, false if not
     */}