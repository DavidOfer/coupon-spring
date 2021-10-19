package com.jb.couponProject.repos;


import com.jb.couponProject.beans.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByUsernameAndIdNot(String username,int id);
    @Query(value = "SELECT c.id FROM customer c WHERE c.username=:username",nativeQuery = true)
    int findIdByUsername(String username);
}