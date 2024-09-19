package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.ProductDetail;
import kidev.vn.onlineshopping.entity.ProductVariant;
import kidev.vn.onlineshopping.model.product.ProductDetailRequestModel;

public interface ProductDetailService {
    ProductDetail findOne(Long id);

    void create(ProductDetail productDetail);

    ProductDetail update(ProductVariant productVariant, ProductDetailRequestModel detailRequestModel);

    void delete(ProductDetail productDetail);
}
