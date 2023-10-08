package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "productRepo")
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product getProductById(Long id);

    Product getProductBySlugAndStatus(String slug, Integer status);
    @Query(value = "SELECT DISTINCT p FROM Product p" +
            " JOIN p.sizes s" +
            " JOIN p.colors c" +
            " JOIN p.productDetails pd" +
            " JOIN p.categories ct" +
            " Where (:name IS NULL OR :name = '' OR p.name LIKE CONCAT('%', :name, '%'))" +
            " AND (:brandId IS NULL OR :brandId = '' OR :brandId = p.brand.id)" +
            " AND (:sizeId IS NULL OR :sizeId = '' OR :sizeId = s.id)" +
            " AND (:colorId IS NULL OR :colorId = '' OR :colorId = c.id)" +
            " AND (:categoryIds IS NULL OR ct.id IN :categoryIds)" +
            " AND (:sale IS NULL OR :sale = FALSE OR pd.offPrice IS NOT NULL)")
    Page<Product>searchProduct(String name,
                               List<String> categoryIds, String brandId,
                               String sizeId, String colorId,
                               Boolean sale, Pageable pageable);
}
