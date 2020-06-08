package com.fintech.demo;

import com.fintech.demo.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineApplication implements CommandLineRunner {

    private final CompanyService companyService;

    @Override
    public void run(String... args) throws Exception {
        companyService.getCompaniesData();
    }
}
