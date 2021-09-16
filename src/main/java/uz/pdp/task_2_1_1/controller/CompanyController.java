package uz.pdp.task_2_1_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2_1_1.entity.Company;
import uz.pdp.task_2_1_1.payload.ApiResponse;
import uz.pdp.task_2_1_1.payload.CompanyDto;
import uz.pdp.task_2_1_1.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PostMapping()
    public ResponseEntity<ApiResponse> addCompany(@Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(409).body(apiResponse);
    }

    @GetMapping()
    public HttpEntity<List<Company>> getCompany(){
        List<Company> company = companyService.getCompany();
        return new HttpEntity<>(company);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Integer id){
        Company company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCompany(@PathVariable Integer id,@RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.editCompany(id, companyDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(409).body(apiResponse);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
