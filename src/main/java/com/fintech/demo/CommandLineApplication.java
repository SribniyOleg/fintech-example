package com.fintech.demo;

import com.fintech.demo.model.Company;
import com.fintech.demo.repository.CompanyRepository;
import com.fintech.demo.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.BlockingDeque;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandLineApplication implements CommandLineRunner {

    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    @Override
    @Async
    public void run(String... args) throws Exception {
        BlockingDeque<String> urls = companyService.saveCompanyUrls(companyService.getCompaniesData());
        List<Company> topByVolume = companyRepository.findTopByVolume();
            long start ;
        while (urls.size() > 0) {
            start = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
            companyService.getCompanyFromRequest(urls.take());
        }


    }
}
