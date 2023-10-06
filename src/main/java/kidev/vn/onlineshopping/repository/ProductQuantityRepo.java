package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "productQuantityRepo")
public interface ProductQuantityRepo extends JpaRepository<ProductQuantity, Long> {
    ProductQuantity getProductQuantityById(Long id);
}
