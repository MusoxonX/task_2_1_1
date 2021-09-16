package uz.pdp.task_2_1_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2_1_1.entity.Address;
import uz.pdp.task_2_1_1.payload.AddressDto;
import uz.pdp.task_2_1_1.payload.ApiResponse;
import uz.pdp.task_2_1_1.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    AddressService addressService;

//    add address
    @PostMapping()
    public HttpEntity<ApiResponse> addAddress(@Valid @RequestBody AddressDto addressDto){
        ApiResponse apiResponse = addressService.addAddress(addressDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(409).body(apiResponse);
    }

//    get all address
    @GetMapping()
    public ResponseEntity<List<Address>> getAddress(){
        List<Address> address = addressService.getAddress();
        return ResponseEntity.ok(address);
    }
//    get Address By Id
    @GetMapping("/{id}")
    public HttpEntity<Address> getAddressById(@PathVariable Integer id){
        Address address = addressService.getAddressById(id);
        return new HttpEntity(address);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editAddress(@Valid @PathVariable Integer id,@RequestBody AddressDto addressDto){
        ApiResponse apiResponse = addressService.editAddress(id, addressDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(409).body(apiResponse);
    }

//    delete address by id
    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteAddress(@PathVariable Integer id){
        ApiResponse apiResponse = addressService.deleteAddress(id);
        return new HttpEntity<>(apiResponse);
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
