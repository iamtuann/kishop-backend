package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Brand;
import kidev.vn.onlineshopping.model.brand.BrandModel;
import kidev.vn.onlineshopping.repository.BrandRepo;
import kidev.vn.onlineshopping.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<BrandModel> findAll() {
        List<Brand> brands = brandRepo.findAll();
        List<BrandModel> brandModels = new ArrayList<>();
        if (brands.size() != 0) {
            for (Brand brand : brands) {
                BrandModel model = new BrandModel(brand);
                brandModels.add(model);
            }
        }
        return brandModels;
    }
}
