package uz.pdp.task_2_1_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task_2_1_1.entity.Address;
import uz.pdp.task_2_1_1.entity.Department;
import uz.pdp.task_2_1_1.entity.Worker;
import uz.pdp.task_2_1_1.payload.ApiResponse;
import uz.pdp.task_2_1_1.payload.WorkerDto;
import uz.pdp.task_2_1_1.repository.AddressRepository;
import uz.pdp.task_2_1_1.repository.DepartmentRepository;
import uz.pdp.task_2_1_1.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    DepartmentRepository departmentRepository;

//    add worker
    public ApiResponse addWorker(WorkerDto workerDto){
        boolean b = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (b) {
            return new ApiResponse("phone number already exists",false);
        }
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent()){
            return new ApiResponse("address not found",false);
        }
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent()){
            return new ApiResponse("department not found",false);
        }

        Worker worker = new Worker();
        worker.setAddress(optionalAddress.get());
        worker.setDepartment(optionalDepartment.get());
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        workerRepository.save(worker);
        return new ApiResponse("worker successfully added",true);
    }

//    get worker
    public List<Worker> getWorker(){
        List<Worker> all = workerRepository.findAll();
        return all;
    }

//    get worker by id
    public Worker getWorkerById(Integer id){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()){
            return optionalWorker.get();
        }
        return null;
    }

    public ApiResponse editWorker(Integer id,WorkerDto workerDto){
        boolean b = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if (b){
            return new ApiResponse("phone number already exists",false);
        }
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent()){
            return new ApiResponse("address not found",false);
        }
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent()){
            return new ApiResponse("department not found",false);
        }

        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent()){
            return new ApiResponse("worker not found",false);
        }
        Worker worker = optionalWorker.get();
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(optionalAddress.get());
        worker.setDepartment(optionalDepartment.get());
        worker.setName(workerDto.getName());
        workerRepository.save(worker);
        return new ApiResponse("worker successfully edited",true);
    }

    public ApiResponse deleteWorker(Integer id){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()){
            workerRepository.deleteById(id);
            return new ApiResponse("worker deleted",true);
        }
        return new ApiResponse("worker not found",false);
    }
}
