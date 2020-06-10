package com.fintech.demo.service;

import com.fintech.demo.dto.CompanyDto;
import com.fintech.demo.model.Company;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletableFuture;

public interface CompanyService {

    List<CompanyDto> getCompaniesData();

    BlockingDeque<String> saveCompanyUrls(List<CompanyDto> companies);

    CompletableFuture<Company> saveCompany(String url);

}
