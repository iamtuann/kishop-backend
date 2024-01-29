package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "categoryRepo")
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category getCategoryById(Long id);

    @Override
    @Query(value = "SELECT c FROM Category c " +
            "WHERE c.type = 0")
    List<Category> findAll();
}
