package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.ProductVariant;

import java.util.List;

public interface ProductVariantService {
    ProductVariant findOne(Long id);

    ProductVariant saveProductVariant(ProductVariant ProductVariant);

    void saveAll(List<ProductVariant> ProductVariants);

    void create(ProductVariant ProductVariant);

    void update(ProductVariant ProductVariant);

    void delete(ProductVariant ProductVariant);
}
