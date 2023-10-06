package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.ProductImage;

import java.util.List;

public interface ProductImageService {
    ProductImage findOne(Long id);

    List<ProductImage> saveAll(List<ProductImage> productImages);

    void create(ProductImage productImage);

    void update(ProductImage productImage);

    void delete(ProductImage productImage);
}
