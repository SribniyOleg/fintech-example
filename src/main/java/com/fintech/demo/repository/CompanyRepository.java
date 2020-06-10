package com.fintech.demo.repository;

import com.fintech.demo.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "SELECT * from company order by volume desc LIMIT 5" , nativeQuery = true)
    List<Company> findTopByVolume();

    @Query(value = "SELECT * from company order by price desc LIMIT 5" , nativeQuery = true)
    List<Company> findTopByPrice();

    Optional<Company> findByUrl(String url);
}
