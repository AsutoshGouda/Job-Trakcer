package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.requestDTO.CompanyRequestDTO;
import com.jobtracker.careerflow.responseDTO.CompanyResponseDTO;
import com.jobtracker.careerflow.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<CompanyResponseDTO> getAllCompanies(){
        return companyService.getAllCompanies();
    }

    @GetMapping("/id/{id}")
    public CompanyResponseDTO getCompanyById(@PathVariable UUID id){
        return companyService.getCompanyById(id);
    }

    @PostMapping
    public CompanyResponseDTO createCompany(@Valid @RequestBody CompanyRequestDTO companyRequestDTO){
        return companyService.save(companyRequestDTO);
    }

    @PatchMapping("/updatecompany/id/{id}")
    public CompanyResponseDTO updateCompany(@PathVariable UUID id, @Valid @RequestBody CompanyRequestDTO companyRequestDTO){
        return companyService.updateCompany(id,companyRequestDTO);
    }

    @DeleteMapping("/deletecompany/id/{id}")
    public void deleteCompany(@PathVariable UUID id){
        companyService.deleteCompany(id);
    }
}
