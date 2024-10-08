package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository(value = "GenderRepo")
public interface GenderRepo extends JpaRepository<Gender, Long> {
    Gender getGenderById(Long id);
}
