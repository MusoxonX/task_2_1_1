package uz.pdp.task_2_1_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2_1_1.entity.Address;
import uz.pdp.task_2_1_1.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    boolean existsByNameAndCompanyId(String name, Integer company_id);
    boolean  existsByNameAndCompanyIdAndIdNot(String name, Integer company_id, Integer id);
}
