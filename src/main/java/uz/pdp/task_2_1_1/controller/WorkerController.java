package uz.pdp.task_2_1_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2_1_1.entity.Worker;
import uz.pdp.task_2_1_1.payload.ApiResponse;
import uz.pdp.task_2_1_1.payload.WorkerDto;
import uz.pdp.task_2_1_1.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;

//    ADD WORKER
    @PostMapping()
    public HttpEntity<ApiResponse> addWoorker(@Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.addWorker(workerDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(409).body(apiResponse);
    }

    @GetMapping()
    public HttpEntity<List<Worker>> getWorker(){
        List<Worker> worker = workerService.getWorker();
        return ResponseEntity.ok(worker);
    }

    @GetMapping("/{id}")
    public HttpEntity<Worker> getWorkerById(@PathVariable Integer id){
        Worker worker = workerService.getWorkerById(id);
        return new HttpEntity<>(worker);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editWorker(@PathVariable Integer id, @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.editWorker(id, workerDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteWorker(@PathVariable Integer id) {
        ApiResponse apiResponse = workerService.deleteWorker(id);
        if (apiResponse.isSuccess()) {
            return ResponseEntity.status(200).body(apiResponse);
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
