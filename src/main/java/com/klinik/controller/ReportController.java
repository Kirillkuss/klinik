package com.klinik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klinik.response.BaseResponse;
import com.klinik.service.report.ReportService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/Reports")
@RestController
@Tag(name = "Report", description = "Отчеты")
public class ReportController {

    @Autowired
    private ReportService service;

    @GetMapping("/reportOne")
    public BaseResponse report(Long id) throws Exception{
        return service.getStatReport(id);
    }
    
}
