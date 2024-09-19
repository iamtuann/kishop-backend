package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.model.product.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product findOne(Long id);

    Product saveProduct(ProductRequest productRequest);

    Page<ProductBasicModel> searchProduct(String name,
                                          List<String> categories, List<String> brandNames,
                                          List<String> sizes, List<String> colors, List<String> genders,
                                          Boolean sale, Pageable pageable);

    ProductModel getProductSellingBySlug(String slug);

    void create(Product product);

    Product update(Product product, ProductVariantRequest variantRequest) throws IOException;

    void update(List<ProductDetailRequest> detailRequests);

    Product update(Product product);

    void delete(Product product);
}
