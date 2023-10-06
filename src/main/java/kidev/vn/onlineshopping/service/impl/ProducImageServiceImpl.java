package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.ProductImage;
import kidev.vn.onlineshopping.repository.ProductImageRepo;
import kidev.vn.onlineshopping.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducImageServiceImpl implements ProductImageService {
    @Autowired
    ProductImageRepo productImageRepo;

    @Override
    public ProductImage findOne(Long id) {
        return productImageRepo.getProductImageById(id);
    }

    @Override
    public List<ProductImage> saveAll(List<ProductImage> productImages) {
        return productImageRepo.saveAll(productImages);
    }

    @Override
    public void create(ProductImage productImage) {
        productImageRepo.save(productImage);
    }

    @Override
    public void update(ProductImage productImage) {
        productImageRepo.save(productImage);
    }

    @Override
    public void delete(ProductImage productImage) {
        productImageRepo.delete(productImage);
    }
}
