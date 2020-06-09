package com.fintech.demo.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.demo.model.Company;
import com.fintech.demo.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class CompanyServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private HttpClient httpClient;

    @InjectMocks
    private CompanyServiceImpl companyService;

//    @Test
//    void shouldGetAllCompanies() throws IOException, InterruptedException {
//        //given
//        List<Company> companies = Collections.singletonList(Company.builder().id(1l).name("name").symbol("AAA").build());
//        //when
//        when(objectMapper.readValue(anyString(), (TypeReference<List<Company>>) any()))
//                .thenReturn(companies);
//        List<Company> result = companyService.getCompaniesData();
//        //then
//        assertEquals(result.get(0), companies.get(0));
//    }
}
