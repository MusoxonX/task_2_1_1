package uz.pdp.task_2_1_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task_2_1_1.entity.Company;
import uz.pdp.task_2_1_1.entity.Department;
import uz.pdp.task_2_1_1.payload.ApiResponse;
import uz.pdp.task_2_1_1.payload.DepartmentDto;
import uz.pdp.task_2_1_1.repository.CompanyRepository;
import uz.pdp.task_2_1_1.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

//    add department
    public ApiResponse addDepartment(DepartmentDto departmentDto){
        boolean b = departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId());
        if (b){
            return new ApiResponse("department already added",false);
        }

        Department department = new Department();
        department.setName(departmentDto.getName());
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent()){
            return new ApiResponse("company not found",false);
        }
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("department successfully added",true);
    }

//  department getting
    public List<Department> getDepartment(){
        List<Department> all = departmentRepository.findAll();
        return all;
    }

//    get department by id
    public Department getDepartmentById(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()){
            return optionalDepartment.get();
        }
        return null;
    }


//    edit department
    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto){
        boolean b = departmentRepository.existsByNameAndCompanyIdAndIdNot(departmentDto.getName(), departmentDto.getCompanyId(), id);
        if (b) {
            return new ApiResponse("department already exists",false);
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("department not found", false);
        }

        Department department = optionalDepartment.get();
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent()){
            return new ApiResponse("company not found",false);
        }
        department.setCompany(optionalCompany.get());
        department.setName(departmentDto.getName());
        departmentRepository.save(department);
        return new ApiResponse("department successfully added",true);
    }


    public ApiResponse deleteDepartment(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()){
            return new ApiResponse("departmetn not found",false);
        }
        departmentRepository.deleteById(id);
        return new ApiResponse("department deleted",true);
    }

}
