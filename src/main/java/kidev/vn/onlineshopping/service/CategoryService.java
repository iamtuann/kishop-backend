package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Category;

public interface CategoryService {
    Category findOne(Long id);

    void create(Category category);

    void update(Category category);

    void delete(Category category);
}
