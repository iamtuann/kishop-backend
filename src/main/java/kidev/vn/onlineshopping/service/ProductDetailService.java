package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.ProductDetail;

public interface ProductDetailService {
    ProductDetail findOne(Long id);

    void create(ProductDetail productDetail);

    void update(ProductDetail productDetail);

    void delete(ProductDetail productDetail);
}
