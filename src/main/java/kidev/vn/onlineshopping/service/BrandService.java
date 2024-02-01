package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Brand;
import kidev.vn.onlineshopping.model.brand.BrandModel;

import java.util.List;

public interface BrandService {
    Brand findOne(Long id);

    void create(Brand brand);

    void update(Brand brand);

    void delete(Brand brand);

    List<BrandModel> findAll();
}
