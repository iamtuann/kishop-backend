package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.model.product.ProductBasicModel;
import kidev.vn.onlineshopping.model.product.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product findOne(Long id);

    void saveProduct(ProductRequest productRequest);

    Page<ProductBasicModel> searchProduct(String name,
                                          List<String> categories, List<String> brandNames,
                                          List<String> colors, List<String> genders,
                                          Boolean sale, Pageable pageable);

    Product getProductSellingBySlug(String slug);

    void create(Product product);

    Product update(Product product);

    void delete(Product product);
}
