package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "brandRepo")
public interface BrandRepo extends JpaRepository<Brand, Long> {
    Brand getBrandById(Long id);
}
