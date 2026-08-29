package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.Exception_Handling.CompanyNotFoundException;
import com.jobtracker.careerflow.entity.CompanyEntity;
import com.jobtracker.careerflow.repository.CompanyRepository;
import com.jobtracker.careerflow.requestDTO.CompanyRequestDTO;
import com.jobtracker.careerflow.responseDTO.CompanyResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public CompanyResponseDTO mapToResponse(CompanyEntity companyEntity){
        return new CompanyResponseDTO(
                companyEntity.getCompanyId(),
                companyEntity.getCompanyName(),
                companyEntity.getWebsite(),
                companyEntity.getIndustry(),
                companyEntity.getLocation(),
                companyEntity.getCreatedAt(),
                companyEntity.getUpdatedAt()
        );
    }

    public CompanyResponseDTO save(CompanyRequestDTO companyRequestDTO){
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyName(companyRequestDTO.companyName());
        companyEntity.setWebsite(companyRequestDTO.website());
        companyEntity.setIndustry(companyRequestDTO.industry());
        companyEntity.setLocation(companyRequestDTO.location());
        companyRepository.save(companyEntity);
        return mapToResponse(companyEntity);
    }

    public List<CompanyResponseDTO> getAllCompanies(){
        return companyRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public CompanyResponseDTO getCompanyById(UUID id){
        CompanyEntity companyEntity = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(
                "Company Not Found!"));
        return mapToResponse(companyEntity);
    }

    public CompanyResponseDTO updateCompany(UUID id, CompanyRequestDTO companyRequestDTO){
        CompanyEntity companyEntity = companyRepository.findById(id).orElseThrow(()-> new CompanyNotFoundException(
                "Company Not Found!"));
        if (!companyRequestDTO.companyName().isEmpty()) {
            companyEntity.setCompanyName(companyRequestDTO.companyName());
        }

        if (!companyRequestDTO.website().isEmpty()) {
            companyEntity.setWebsite(companyRequestDTO.website());
        }

        if (!companyRequestDTO.location().isEmpty()) {
            companyEntity.setLocation(companyRequestDTO.location());
        }

        if (!companyRequestDTO.industry().isEmpty()) {
            companyEntity.setIndustry(companyRequestDTO.industry());
        }

        companyRepository.save(companyEntity);
        return mapToResponse(companyEntity);
    }

    public void deleteCompany(UUID companyId){
        CompanyEntity companyEntity =
                companyRepository.findById(companyId).orElseThrow(()-> new CompanyNotFoundException("Specified UUID doesn't belong to any company!!"));
        companyRepository.delete(companyEntity);
    }

}
