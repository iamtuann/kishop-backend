package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Category;
import kidev.vn.onlineshopping.model.category.CategoryModel;

import java.util.List;

public interface CategoryService {
    Category findOne(Long id);

    void create(Category category);

    CategoryModel saveCategory(Category category);

    void update(Category category);

    void delete(Category category);

    List<CategoryModel> findAll();
}
