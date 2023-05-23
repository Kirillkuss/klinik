package com.klinik.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/Reports")
@RestController
@Tag(name = "Report", description = "Отчеты")
public class ReportController {

    @GetMapping("/reportOne")
    public String report() throws Exception{
        return "Report";
    }
    
}
