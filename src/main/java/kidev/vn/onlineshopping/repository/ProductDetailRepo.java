package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "productDetailRepo")
public interface ProductDetailRepo extends JpaRepository<ProductDetail, Long> {
    ProductDetail getProductDetailById(Long id);

    @Query(value = "SELECT pd FROM ProductDetail pd " +
            "JOIN pd.color c " +
            "JOIN pd.product p " +
            "JOIN p.gender g " +
            "JOIN p.categories ct " +
            "JOIN p.sizes s " +
            "WHERE (:name IS NULL OR :name = '' OR p.name LIKE CONCAT('%', :name, '%')) " +
            "AND (coalesce(:brandNames, null) IS NULL OR p.brand.name IN (:brandNames)) " +
            "AND (coalesce(:sizes, null) IS NULL OR s.name IN (:sizes)) " +
            "AND (coalesce(:colors, null) IS NULL OR c.engName IN (:colors)) " +
            "AND (coalesce(:categories, null) IS NULL OR ct.slug IN (:categories)) " +
            "AND (coalesce(:genders, null) IS NULL OR g.name IN (:genders)) " +
            "AND (:sale IS NULL OR :sale = FALSE OR pd.price < pd.oldPrice) " +
            "GROUP BY pd.product")
    Page<ProductDetail> searchProduct(@Param(value = "name") String name,
                                @Param(value = "categories") List<String> categories,
                                @Param(value = "brandNames") List<String> brandNames,
                                @Param(value = "sizes") List<String> sizes,
                                @Param(value = "colors") List<String> colors,
                                @Param(value = "genders") List<String> genders,
                                Boolean sale, Pageable pageable);

//    @Query(value = "select pd.* from ( " +
//            "select DISTINCT pd.*, p.created_date as created_date, " +
//            "ROW_NUMBER() OVER (PARTITION BY pd.product_id ORDER BY pd.id) as row_num " +
//            "from product_detail pd " +
//            "inner join color c on pd.color_id = c.id " +
//            "inner join product p on p.id = pd.product_id " +
//            "inner join brand b on p.brand_id = b.id " +
//            "inner join product_category pct on pct.product_id = p.id " +
//            "inner join category ct on pct.category_id = ct.id " +
//            "inner join product_gender pg on pg.product_id = p.id " +
//            "inner join gender g on g.id = pg.gender_id " +
//            "inner join product_size ps on ps.product_id = p.id " +
//            "inner join size s on s.id = ps.size_id " +
//            "WHERE (:name IS NULL OR :name = '' OR p.name LIKE CONCAT('%', :name, '%')) " +
//            "AND (coalesce(:brandNames, null) IS NULL OR b.name IN (:brandNames)) " +
//            "AND (coalesce(:sizes, null) IS NULL OR s.name IN (:sizes)) " +
//            "AND (coalesce(:colors, null) IS NULL OR c.eng_name IN (:colors)) " +
//            "AND (coalesce(:categories, null) IS NULL OR ct.slug IN (:categories)) " +
//            "AND (coalesce(:genders, null) IS NULL OR g.name IN (:genders)) " +
//            "AND (:sale IS NULL OR :sale = FALSE OR pd.price < pd.old_price) " +
//            ") as pd where row_num = 1 " +
//            "ORDER BY \n#pageable\n ", nativeQuery = true)
//    Page<ProductDetail> searchProduct(@Param(value = "name") String name,
//                                      @Param(value = "categories") List<String> categories,
//                                      @Param(value = "brandNames") List<String> brandNames,
//                                      @Param(value = "sizes") List<String> sizes,
//                                      @Param(value = "colors") List<String> colors,
//                                      @Param(value = "genders") List<String> genders,
//                                      Boolean sale, Pageable pageable);
}
