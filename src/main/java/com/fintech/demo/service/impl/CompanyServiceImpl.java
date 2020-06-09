package com.fintech.demo.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.demo.dto.CompanyDto;
import com.fintech.demo.model.Company;
import com.fintech.demo.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

import static com.fintech.demo.util.Constant.ALL_COMPANIES_URL;
import static com.fintech.demo.util.Constant.COMPANY_INFO_URL;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;


    @SneakyThrows
    @Override
    public List<CompanyDto> getCompaniesData() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(ALL_COMPANIES_URL))
                .build();

        List<CompanyDto> companies = objectMapper.readValue(
                httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body(),
                new TypeReference<List<CompanyDto>>() {
                });
        companies = companies.stream().filter(c -> c.isState()).collect(Collectors.toList());
        BlockingDeque<String> companyUrls = getCompanyUrls(companies);
        return companies;
    }


    @Async
    private BlockingDeque<String> getCompanyUrls(List<CompanyDto> companies) {
        BlockingDeque<String> urls = new LinkedBlockingDeque<>(companies.size());
        for (CompanyDto company : companies) {
            urls.add(String.format(COMPANY_INFO_URL , company.getSymbol()));
        }
        return urls;
    }


}
