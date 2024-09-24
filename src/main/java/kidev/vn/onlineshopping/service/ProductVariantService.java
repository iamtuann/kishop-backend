package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.entity.ProductVariant;
import kidev.vn.onlineshopping.model.product.ProductDetailRequest;
import kidev.vn.onlineshopping.model.product.ProductVariantRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ProductVariantService {
    ProductVariant findOne(Long id);

    Page<ProductVariant> searchProduct(String name,
                                       List<String> categories,
                                       List<String> brandNames,
                                       List<String> colors,
                                       List<String> genders,
                                       Boolean sale, Pageable pageable);

    void saveProductVariant(ProductVariantRequest model, Product product) throws IOException;


    void create(ProductVariant ProductVariant);

    void update(ProductVariant ProductVariant);

    void update(ProductDetailRequest detailRequest);

    void delete(ProductVariant ProductVariant);
}
