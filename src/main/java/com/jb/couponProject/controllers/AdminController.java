package com.jb.couponProject.controllers;


import com.jb.couponProject.Exceptions.CompanyExceptions.CompanyException;
import com.jb.couponProject.Exceptions.CompanyExceptions.CompanyNotFound;
import com.jb.couponProject.Exceptions.CustomerExceptions.CustomerException;
import com.jb.couponProject.Exceptions.CustomerExceptions.CustomerNotFound;
import com.jb.couponProject.beans.Company;
import com.jb.couponProject.beans.Customer;
import com.jb.couponProject.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

/**
 * a REST api controller for exposing our admin user end points
 */

@RestController
@RequestMapping("/admin")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class AdminController {
    private final AdminService adminService;


    @PostMapping("addCompany")
    public ResponseEntity<?> addCompany(@RequestBody Company company) throws CompanyException {
        try {
            return new ResponseEntity<>(adminService.addCompany(company),HttpStatus.OK);
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
            throw new CompanyException(e.getMessage());
        }
    }

    @PutMapping("updateCompany")
    public ResponseEntity<?> updateCompany(@RequestBody Company company) throws CompanyException {
        try {
            adminService.updateCompany(company);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
            throw new CompanyException(e.getMessage());
        }
    }
    @PostMapping("deleteCompany")
    public ResponseEntity<?> deleteCompany(@RequestBody int companyId) {
            adminService.deleteCompany(companyId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/getAllCompany")
    public ResponseEntity<?> getAllCompany() {
        return ResponseEntity.ok(adminService.getAllCompanies());
    }

    @PostMapping("/getOneCompany")
    public ResponseEntity<?> getOneCompany(@RequestBody int companyId) throws CompanyException {
        try {
            return ResponseEntity.ok(adminService.getOneCompany(companyId));
        } catch (CompanyNotFound e) {
            throw new CompanyException(e.getMessage());
        }
    }

    @PostMapping("addCustomer")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) throws CustomerException {
        try {
            return new ResponseEntity<>(adminService.addCustomer(customer),HttpStatus.OK);
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
            throw new CustomerException(e.getMessage());
        }
    }

    @PutMapping("updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) throws CustomerException {
        try {
            adminService.updateCustomer(customer);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
            throw new CustomerException(e.getMessage());
        }
    }
    @PostMapping("deleteCustomer")
    public ResponseEntity<?> deleteCustomer(@RequestBody int customerId) {
        adminService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PostMapping("/getAllCustomer")
    public ResponseEntity<?> getAllCustomer() {

        return ResponseEntity.ok(adminService.getAllCustomers());
    }

    @PostMapping("/getOneCustomer")
    public ResponseEntity<?> getOneCustomer(@RequestBody int customerId) throws CustomerException {
        try {
            return ResponseEntity.ok(adminService.getOneCustomer(customerId));
        } catch (CustomerNotFound e) {
            throw new CustomerException(e.getMessage());
        }
    }
}
