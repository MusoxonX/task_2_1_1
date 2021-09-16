package uz.pdp.task_2_1_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task_2_1_1.entity.Address;
import uz.pdp.task_2_1_1.payload.AddressDto;
import uz.pdp.task_2_1_1.payload.ApiResponse;
import uz.pdp.task_2_1_1.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;


//    adding address with addressDto

    public ApiResponse addAddress(AddressDto addressDto){
        boolean exists = addressRepository.existsByStreetAndAndHomeNumber(addressDto.getStreet(), addressDto.getHomeNumber());
        if (exists){
            return new ApiResponse("like this address already exists",false);
        }
        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("address added",true);
    }

//    get all Address list
    public List<Address> getAddress(){
        List<Address> all = addressRepository.findAll();
        return all;
    }
//    get address by Id
    public Address getAddressById(Integer id){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent()){
            return null;
        }
        return optionalAddress.get();
    }


//    edit addess by id
    public ApiResponse editAddress(Integer id,AddressDto addressDto){
        boolean b = addressRepository.existsByHomeNumberAndStreetAndIdNot(addressDto.getHomeNumber(), addressDto.getStreet(), id);
        if (b){
            return new ApiResponse("address already exists",false);
        }
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent()){
            return new ApiResponse("address not found",false);
        }
        Address address = optionalAddress.get();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("address edited",true);
    }

//    delete address by id
    public ApiResponse deleteAddress(Integer id){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()){
            addressRepository.deleteById(id);
            return new ApiResponse("address deleted",true);
        }
        return new ApiResponse("address not found",false);
    }
}
