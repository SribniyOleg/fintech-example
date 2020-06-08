package com.fintech.demo.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.demo.model.Company;
import com.fintech.demo.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import static com.fintech.demo.util.Constant.GET_ALL_COMPANIES_URL;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;


    @SneakyThrows
    @Override
    public List<Company> getCompaniesData() {
        long start = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(GET_ALL_COMPANIES_URL))
                .build();

        List<Company> companies = objectMapper.readValue(
                httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body(),
                new TypeReference<List<Company>>() {
                });
        companies = companies.stream().filter(c -> c.isState()).collect(Collectors.toList());
        long end = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() - start;
        return companies;
    }
}
