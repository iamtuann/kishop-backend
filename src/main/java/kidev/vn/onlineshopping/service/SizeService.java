package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Size;

public interface SizeService {
    Size findOne(Long id);

    void create(Size product);

    void update(Size product);

    void delete(Size product);
}
