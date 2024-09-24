package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "productVariantRepo")
public interface ProductVariantRepo extends JpaRepository<ProductVariant, Long> {
    ProductVariant getProductVariantById(Long id);

//    @Query(value = "SELECT pv FROM ProductVariant pv " +
//            "JOIN pv.color c " +
//            "JOIN pv.product p " +
//            "JOIN p.gender g " +
//            "JOIN p.categories ct " +
//            "JOIN p.sizes s " +
//            "WHERE (:name IS NULL OR :name = '' OR p.name LIKE CONCAT('%', :name, '%')) " +
//            "AND (coalesce(:brandNames, null) IS NULL OR p.brand.name IN (:brandNames)) " +
//            "AND (coalesce(:sizes, null) IS NULL OR s.name IN (:sizes)) " +
//            "AND (coalesce(:colors, null) IS NULL OR c.engName IN (:colors)) " +
//            "AND (coalesce(:categories, null) IS NULL OR ct.slug IN (:categories)) " +
//            "AND (coalesce(:genders, null) IS NULL OR g.name IN (:genders)) " +
//            "AND (:sale IS NULL OR :sale = FALSE OR pv.price < pv.oldPrice) " +
//            "GROUP BY pv.product")
//    Page<ProductDetail> searchProduct(@Param(value = "name") String name,
//                                @Param(value = "categories") List<String> categories,
//                                @Param(value = "brandNames") List<String> brandNames,
//                                @Param(value = "sizes") List<String> sizes,
//                                @Param(value = "colors") List<String> colors,
//                                @Param(value = "genders") List<String> genders,
//                                Boolean sale, Pageable pageable);

    @Query(value = "select pv.* from ( " +
            "select DISTINCT pv.*, p.created_date as createdDate, pd.sold as sold, " +
            "ROW_NUMBER() OVER (PARTITION BY pv.product_id ORDER BY pv.id) as row_num " +
            "from product_variant pv " +
            "inner join product_variant_color pvc on pvc.product_variant_id = pv.id " +
            "inner join color c on pvc.color_id = c.id " +
            "inner join product p on p.id = pv.product_id " +
            "inner join brand b on p.brand_id = b.id " +
            "inner join product_category pct on pct.product_id = p.id " +
            "inner join category ct on pct.category_id = ct.id " +
            "inner join product_gender pg on pg.product_id = p.id " +
            "inner join gender g on g.id = pg.gender_id " +
            "inner join product_detail pd on pv.id = pd.product_variant_id " +
            "WHERE (:name IS NULL OR :name = '' OR p.name LIKE CONCAT('%', :name, '%')) " +
            "AND (coalesce(:brandNames, null) IS NULL OR b.name IN (:brandNames)) " +
            "AND (coalesce(:colors, null) IS NULL OR c.eng_name IN (:colors)) " +
            "AND (coalesce(:categories, null) IS NULL OR ct.slug IN (:categories)) " +
            "AND (coalesce(:genders, null) IS NULL OR g.name IN (:genders)) " +
            "AND (:sale IS NULL OR :sale = FALSE OR pv.price < pv.old_price) " +
            ") as pv where row_num = 1 ",
            countQuery = "select count(*) from product_variant",
            nativeQuery = true)
    Page<ProductVariant> searchProduct(@Param(value = "name") String name,
                                       @Param(value = "categories") List<String> categories,
                                       @Param(value = "brandNames") List<String> brandNames,
                                       @Param(value = "colors") List<String> colors,
                                       @Param(value = "genders") List<String> genders,
                                       Boolean sale, Pageable pageable);
}
