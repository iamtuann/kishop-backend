package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Brand;

public interface BrandService {
    Brand findOne(Long id);

    void create(Brand brand);

    void update(Brand brand);

    void delete(Brand brand);
}
