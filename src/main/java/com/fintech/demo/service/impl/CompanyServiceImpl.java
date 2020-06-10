package com.fintech.demo.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.demo.dto.CompanyDto;
import com.fintech.demo.model.Company;
import com.fintech.demo.model.CompanyLog;
import com.fintech.demo.repository.CompanyLogRepository;
import com.fintech.demo.repository.CompanyRepository;
import com.fintech.demo.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletableFuture;
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
    private final CompanyRepository companyRepository;
    private final BlockingDeque<String> urls;
    private final CompanyLogRepository companyLogRepository;


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
        return companies;
    }

    @SneakyThrows
    @Async
    public CompletableFuture<Company> saveCompany(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
        String companyJson = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        Company company = objectMapper.readValue(companyJson, Company.class);
        Optional<Company> companyOptional = companyRepository.findByUrl(url);
        if (companyOptional.isPresent()) {
            Company oldCompany = companyOptional.get();
            company = saveUserIfExist(oldCompany, company);
        } else {
        }
        company.setUrl(url);
        company = companyRepository.save(company);
        return CompletableFuture.completedFuture(company);
    }

    @Async
    public void getTopCompanies() {
        List<Company> topByVolume = companyRepository.findTopByVolume();
        List<Company> topByPrice = companyRepository.findTopByPrice();
        log.info("Tom companies by volume");
        for (Company c : topByVolume) {
            log.info("   -" + c.getName() + " - " + c.getVolume());
        }

        log.info("Tom companies by price");
        for (Company c : topByPrice) {
            log.info("   -" + c.getName() + " - " + c.getPrice());
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void showTopCompanies() {
        getTopCompanies();
    }

    public BlockingDeque<String> saveCompanyUrls(List<CompanyDto> companies) {
        for (CompanyDto company : companies) {
            urls.add(String.format(COMPANY_INFO_URL, company.getSymbol()));
        }
        return urls;
    }

    private Company saveUserIfExist(Company old, Company newCompany) {
        CompanyLog log = new CompanyLog();
        if (old.getPrice() != newCompany.getPrice()) {
            log.setOldPrice(old.getPrice());
            log.setNewPrice(newCompany.getPrice());
            old.setPrice(newCompany.getPrice());
        }
        if (old.getVolume() != newCompany.getVolume()) {
            log.setOldVolume(old.getVolume());
            log.setNewVolume(newCompany.getVolume());
            old.setVolume(newCompany.getVolume());
        }
        log.setCompany(old);
        companyLogRepository.save(log);
        return companyRepository.save(old);
    }

}
