package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.ProductQuantity;
import kidev.vn.onlineshopping.repository.ProductQuantityRepo;
import kidev.vn.onlineshopping.service.ProductQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductQuantityServiceImpl implements ProductQuantityService {
    @Autowired
    ProductQuantityRepo productQuantityRepo;


    @Override
    public ProductQuantity findOne(Long id) {
        return productQuantityRepo.getProductQuantityById(id);
    }

    @Override
    public void create(ProductQuantity productQuantity) {
        productQuantityRepo.save(productQuantity);
    }

    @Override
    public void update(ProductQuantity productQuantity) {
        productQuantityRepo.save(productQuantity);
    }

    @Override
    public void delete(ProductQuantity productQuantity) {
        productQuantityRepo.delete(productQuantity);
    }
}
