package com.jb.couponProject.services;

import com.jb.couponProject.Exceptions.CompanyExceptions.CompanyException;
import com.jb.couponProject.Exceptions.CompanyExceptions.CompanyNotFound;
import com.jb.couponProject.Exceptions.CustomerExceptions.CustomerException;
import com.jb.couponProject.Exceptions.CustomerExceptions.CustomerNotFound;
import com.jb.couponProject.beans.Admin;
import com.jb.couponProject.beans.Company;
import com.jb.couponProject.beans.Customer;
import com.jb.couponProject.repos.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 *  the services layer for our admin entities.
 */
@RequiredArgsConstructor
@Service
public class AdminService extends ClientService implements UserDetailsService {
    private final AdminRepo adminRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepo.findByUsername(username)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format("Username %s not found",username)));
    }

    public void addAdmin(Admin admin) throws Exception {
        if (adminRepo.existsByUsername(admin.getUsername())) {
            throw new Exception("cannot add admin with the same name");
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepo.save(admin);
    }

    public int addCompany(Company company) throws CompanyException {
        if (companyRepo.existsByUsername(company.getUsername())) {
            throw new CompanyException("cannot add company - a company with that email already exists");
        }
        if (companyRepo.existsByName(company.getName())) {
            throw new CompanyException("cannot add company - a company with that name already exists");
        }
        company.setPassword(passwordEncoder.encode(company.getPassword()));
        company.setRole("ROLE_COMPANY");
        company.setAuthorities(null);
        return companyRepo.save(company).getId();
    }

    public void updateCompany(Company company) throws CompanyException
    {
        Company dbCompany = companyRepo.findById(company.getId()).orElseThrow(CompanyNotFound::new);
        if (companyRepo.existsByUsernameAndIdNot(company.getUsername(), company.getId())) {
            throw new CompanyException("cannot update company - a company with that email already exists");
        }
        if (companyRepo.existsByNameAndIdNot(company.getName(), company.getId())) {
            throw new CompanyException("cannot update company - a company with that name already exists");
        }
        dbCompany.setUsername(company.getUsername());
        dbCompany.setPassword(passwordEncoder.encode(company.getPassword()));
        /*
        org.hibernate.HibernateException: A collection with
        cascade="all-delete-orphan" was no longer referenced by the owning entity instance:
        dbCompany.setCoupons(company.getCoupons());
         */
        companyRepo.save(dbCompany);
    }

    public void deleteCompany(int companyID) {
        couponsRepo.findByCompanyId(companyID).forEach(coupon -> couponsRepo.deleteCouponPurchasesByCouponId(coupon.getId()));
        companyRepo.deleteById(companyID);
    }

    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    public Company getOneCompany(int companyID) throws CompanyNotFound {
        return companyRepo.findById(companyID).orElseThrow(CompanyNotFound::new);
    }

    public int addCustomer(Customer customer) throws CustomerException {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        if(customerRepo.existsByUsername(customer.getUsername()))
        {
            throw new CustomerException("cannot add customer - a customer with that email already exists");
        }
        customer.setRole("ROLE_CUSTOMER");
        customer.setAuthorities(null);
        return customerRepo.save(customer).getId();
    }

    public void updateCustomer(Customer customer) throws CustomerException
    {
        if (customerRepo.existsByUsernameAndIdNot(customer.getUsername(), customer.getId())) {
            throw new CustomerException("cannot update customer - a customer with that email already exists");
        }
        Customer updateCustomer = customerRepo.findById(customer.getId()).orElseThrow(CustomerNotFound::new);
        updateCustomer.setFirstName(customer.getFirstName());
        updateCustomer.setLastName(customer.getLastName());
        updateCustomer.setUsername(customer.getUsername());
        updateCustomer.setPassword(passwordEncoder.encode(customer.getPassword()));
        updateCustomer.setCoupons(customer.getCoupons());
        customerRepo.save(updateCustomer);
    }

    public void deleteCustomer(int customerID) {
        couponsRepo.deleteCouponPurchasesByCustomerId(customerID);
        customerRepo.deleteById(customerID);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer getOneCustomer(int customerID) throws CustomerNotFound {
        return customerRepo.findById(customerID).orElseThrow(CustomerNotFound::new);
    }

    public int findIdByUsername(String username)
    {
        return adminRepo.findIdByUsername(username);
    }
}
