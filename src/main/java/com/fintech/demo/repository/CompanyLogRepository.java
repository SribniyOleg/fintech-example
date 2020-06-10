package com.fintech.demo.repository;


import com.fintech.demo.model.CompanyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyLogRepository extends JpaRepository<CompanyLog, Long> {
}
