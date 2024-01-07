package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.model.product.ProductBasicModel;
import kidev.vn.onlineshopping.model.product.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product findOne(Long id);

    Product saveProduct(Product product);

    Page<ProductBasicModel> searchProduct(String name,
                                          List<Long> categoryIds, String brandId,
                                          String sizeId, String colorId,
                                          Boolean sale, Pageable pageable);

    ProductModel getProductSellingtBySlug(String slug);

    List<ProductBasicModel> getTopProductByCreatedDate(Pageable pageable);

    void create(Product product);

    void update(Product product);

    void delete(Product product);
}
