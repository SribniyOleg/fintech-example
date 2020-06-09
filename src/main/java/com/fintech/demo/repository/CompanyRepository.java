package com.fintech.demo.repository;

import com.fintech.demo.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "SELECT * from company order by previous_volume desc LIMIT 5" , nativeQuery = true)
    List<Company> findTopByVolume();

    @Query(value = "SELECT * from company order by price desc LIMIT 5" , nativeQuery = true)
    List<Company> findTopByPrice();
}
