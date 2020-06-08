package com.fintech.demo.service.impl;

import com.fintech.demo.model.Company;
import com.fintech.demo.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CompanyServiceImpl implements CompanyService {
    @Override
    public List<Company> getCompaniesData() {
        return null;
    }
}
