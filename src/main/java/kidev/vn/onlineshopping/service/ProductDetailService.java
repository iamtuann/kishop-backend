package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.ProductDetail;

import java.util.List;

public interface ProductDetailService {
    ProductDetail findOne(Long id);

    ProductDetail saveProductDetail(ProductDetail productDetail);

    void saveAll(List<ProductDetail> productDetails);

    void create(ProductDetail productDetail);

    void update(ProductDetail productDetail);

    void delete(ProductDetail productDetail);
}
