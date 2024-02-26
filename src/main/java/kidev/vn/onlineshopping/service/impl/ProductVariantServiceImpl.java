package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.ProductVariant;
import kidev.vn.onlineshopping.repository.ProductVariantRepo;
import kidev.vn.onlineshopping.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {
    @Autowired
    ProductVariantRepo productVariantRepo;


    @Override
    public ProductVariant findOne(Long id) {
        return productVariantRepo.getProductVariantById(id);
    }

    @Override
    public ProductVariant saveProductVariant(ProductVariant ProductVariant) {
        return productVariantRepo.save(ProductVariant);
    }

    @Override
    public void saveAll(List<ProductVariant> ProductVariants) {
        productVariantRepo.saveAll(ProductVariants);
    }

    @Override
    public void create(ProductVariant ProductVariant) {
        productVariantRepo.save(ProductVariant);
    }

    @Override
    public void update(ProductVariant ProductVariant) {
        productVariantRepo.save(ProductVariant);
    }

    @Override
    public void delete(ProductVariant ProductVariant) {
        productVariantRepo.delete(ProductVariant);
    }
}
