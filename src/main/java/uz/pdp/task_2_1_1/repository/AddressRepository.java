package uz.pdp.task_2_1_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2_1_1.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    boolean existsByStreetAndAndHomeNumber(String street, Integer homeNumber);

    boolean existsByHomeNumberAndStreetAndIdNot(Integer homeNumber, String street, Integer id);
}
