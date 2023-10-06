package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "productDetailRepo")
public interface ProductDetailRepo extends JpaRepository<ProductDetail, Long> {
    ProductDetail getProductDetailById(Long id);
}
