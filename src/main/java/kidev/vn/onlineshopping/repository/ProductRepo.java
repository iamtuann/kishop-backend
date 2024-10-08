package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository(value = "productRepo")
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product getProductById(Long id);

    @Query(value = "SELECT p FROM Product p")
    Page<Product> getTopProductByCreatedDate(Pageable pageable);

    Product getProductBySlugAndStatus(String slug, Integer status);

//    @Query(value = "SELECT DISTINCT p FROM Product p" +
//            " JOIN p.sizes s" +
//            " JOIN p.colors c" +
//            " JOIN p.productVariants pv" +
//            " JOIN p.categories ct" +
//            " JOIN p.gender g" +
//            " Where (:name IS NULL OR :name = '' OR p.name LIKE CONCAT('%', :name, '%'))" +
//            " AND (coalesce(:brandNames, null) IS NULL OR p.brand.name IN (:brandNames))" +
//            " AND (coalesce(:sizes, null) IS NULL OR s.name IN (:sizes))" +
//            " AND (coalesce(:colors, null) IS NULL OR c.engName IN (:colors))" +
//            " AND (coalesce(:categories, null) IS NULL OR ct.slug IN (:categories))" +
//            " AND (coalesce(:genders, null) IS NULL OR g.name IN (:genders))" +
//            " AND (:sale IS NULL OR :sale = FALSE OR pv.oldPrice IS NOT NULL) ")
//    Page<Product>searchProduct(@Param(value = "name") String name,
//                               @Param(value = "categories") List<String> categories,
//                               @Param(value = "brandNames") List<String> brandNames,
//                               @Param(value = "sizes") List<String> sizes,
//                               @Param(value = "colors") List<String> colors,
//                               @Param(value = "genders") List<String> genders,
//                               Boolean sale, Pageable pageable);
}
