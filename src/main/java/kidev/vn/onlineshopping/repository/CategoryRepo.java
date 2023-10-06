package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "categoryRepo")
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category getCategoryById(Long id);
}
