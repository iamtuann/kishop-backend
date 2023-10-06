package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "sizeRepo")
public interface SizeRepo extends JpaRepository<Size, Long> {
    Size getSizeById(Long id);
}
