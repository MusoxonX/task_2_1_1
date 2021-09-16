package uz.pdp.task_2_1_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2_1_1.entity.Department;
import uz.pdp.task_2_1_1.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker,Integer> {
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}
