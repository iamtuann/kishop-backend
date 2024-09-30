package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.ProductDetail;
import kidev.vn.onlineshopping.entity.ProductVariant;
import kidev.vn.onlineshopping.model.productDetail.ProductDetailRequest;

public interface ProductDetailService {
    ProductDetail findOne(Long id);

    void create(ProductDetail productDetail);

    ProductDetail save(ProductVariant productVariant, ProductDetailRequest detailRequestModel);

    void delete(ProductDetail productDetail);
}
