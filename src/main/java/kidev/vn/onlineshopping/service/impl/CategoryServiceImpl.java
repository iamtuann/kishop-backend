package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Category;
import kidev.vn.onlineshopping.model.category.CategoryModel;
import kidev.vn.onlineshopping.repository.CategoryRepo;
import kidev.vn.onlineshopping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;


    @Override
    public Category findOne(Long id) {
        return categoryRepo.getCategoryById(id);
    }

    @Override
    public void create(Category category) {
        categoryRepo.save(category);
    }

    @Override
    public CategoryModel saveCategory(Category category) {
        Category cate = categoryRepo.save(category);
        return new CategoryModel(cate);
    }

    @Override
    public void update(Category category) {
        categoryRepo.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepo.delete(category);
    }

    @Override
    public List<CategoryModel> findAll() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryModel> categoryModels = new ArrayList<>();
        if (categories.size() > 0) {
            for (Category cate : categories) {
                categoryModels.add(new CategoryModel(cate));
            }
        }
        return categoryModels;
    }
}
