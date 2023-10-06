package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.ProductQuantity;

public interface ProductQuantityService {
    ProductQuantity findOne(Long id);

    void create(ProductQuantity productQuantity);

    void update(ProductQuantity productQuantity);

    void delete(ProductQuantity productQuantity);
}
