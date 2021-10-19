package com.jb.couponProject.repos;

import com.jb.couponProject.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company,Integer> {
    Optional<Company> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByName(String name);
    boolean existsByUsernameAndIdNot(String username,int id);
    boolean existsByNameAndIdNot(String name,int id);
    @Query(value = "SELECT c.id FROM company c WHERE c.username=:username",nativeQuery = true)
    int findIdByUsername(String username);
}
