package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.ProductDetail;
import kidev.vn.onlineshopping.repository.ProductDetailRepo;
import kidev.vn.onlineshopping.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    ProductDetailRepo productDetailRepo;


    @Override
    public ProductDetail findOne(Long id) {
        return productDetailRepo.getProductDetailById(id);
    }

    @Override
    public void create(ProductDetail productDetail) {
        productDetailRepo.save(productDetail);
    }

    @Override
    public void update(ProductDetail productDetail) {
        productDetailRepo.save(productDetail);
    }

    @Override
    public void delete(ProductDetail productDetail) {
        productDetailRepo.delete(productDetail);
    }
}
