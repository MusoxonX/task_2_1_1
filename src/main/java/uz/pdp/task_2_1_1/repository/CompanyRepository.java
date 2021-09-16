package uz.pdp.task_2_1_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2_1_1.entity.Address;
import uz.pdp.task_2_1_1.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
    boolean existsByCorpNameAndDirectorName(String corpName, String directorName);
    boolean existsByCorpNameAndDirectorNameAndIdNot(String corpName, String directorName, Integer id);
}
