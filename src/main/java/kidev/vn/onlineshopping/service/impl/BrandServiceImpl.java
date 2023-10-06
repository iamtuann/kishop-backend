package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Brand;
import kidev.vn.onlineshopping.repository.BrandRepo;
import kidev.vn.onlineshopping.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepo brandRepo;


    @Override
    public Brand findOne(Long id) {
        return brandRepo.getBrandById(id);
    }

    @Override
    public void create(Brand brand) {
        brandRepo.save(brand);
    }

    @Override
    public void update(Brand brand) {
        brandRepo.save(brand);
    }

    @Override
    public void delete(Brand brand) {
        brandRepo.delete(brand);
    }
}
