package uz.pdp.task_2_1_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.task_2_1_1.entity.Address;
import uz.pdp.task_2_1_1.entity.Company;
import uz.pdp.task_2_1_1.payload.ApiResponse;
import uz.pdp.task_2_1_1.payload.CompanyDto;
import uz.pdp.task_2_1_1.repository.AddressRepository;
import uz.pdp.task_2_1_1.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;

    //    company add
    public ApiResponse addCompany(CompanyDto companyDto){
        boolean b = companyRepository.existsByCorpNameAndDirectorName(companyDto.getCorpName(), companyDto.getDirectorName());
        if (b) {
            return new ApiResponse("company already exists",false);
        }
        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent()){
            return new ApiResponse("Address not found",false);
        }
        company.setAddress(optionalAddress.get());
        companyRepository.save(company);
        return new ApiResponse("company successfully added",true);
    }


//    company get
    public List<Company> getCompany(){
        List<Company> all = companyRepository.findAll();
        return all;
    }



//    company get by id
    public Company getCompanyById(Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()){
            return optionalCompany.get();
        }
        return null;
    }

    public ApiResponse editCompany(Integer id,CompanyDto companyDto){
        boolean b = companyRepository.existsByCorpNameAndDirectorNameAndIdNot(companyDto.getCorpName(), companyDto.getDirectorName(), id);
        if (b) {
            return new ApiResponse("company already exists",false);
        }
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()){
            return new ApiResponse("company not found",false);
        }
        Company company = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent()){
            return new ApiResponse("address not found",false);
        }
        company.setAddress(optionalAddress.get());
        companyRepository.save(company);
        return new ApiResponse("company successfully edited",true);
    }
}
