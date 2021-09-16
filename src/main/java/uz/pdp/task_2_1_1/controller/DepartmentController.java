package uz.pdp.task_2_1_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2_1_1.entity.Department;
import uz.pdp.task_2_1_1.payload.ApiResponse;
import uz.pdp.task_2_1_1.payload.DepartmentDto;
import uz.pdp.task_2_1_1.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

//    add department
    @PostMapping()
    public HttpEntity<ApiResponse> addDepartment(@Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.addDepartment(departmentDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(409).body(apiResponse);
    }
//   get department
    @GetMapping()
    public HttpEntity<?> getDepartment(){
        List<Department> department = departmentService.getDepartment();
        return new HttpEntity<>(department);
    }
//  get department by id
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Integer id){
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

//    edit department
    @PutMapping("/{id}")
    public HttpEntity<?> editDepartment(@Valid @PathVariable Integer id,@RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.editDepartment(id, departmentDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(409).body(apiResponse);
    }

//    delete department
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletDepartment(@PathVariable Integer id){
        ApiResponse apiResponse = departmentService.deleteDepartment(id);
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
