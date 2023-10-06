package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "productImageRepo")
public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {
    ProductImage getProductImageById(Long id);
}
