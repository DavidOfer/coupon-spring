package com.jb.couponProject.repos;

import com.jb.couponProject.beans.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin,Integer> {
    Optional<Admin> findByUsername(String username);
    boolean existsByUsername(String username);
    @Query(value = "SELECT c.id FROM admin c WHERE c.username=:username",nativeQuery = true)
    int findIdByUsername(String username);
}
