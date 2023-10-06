package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Category;
import kidev.vn.onlineshopping.repository.CategoryRepo;
import kidev.vn.onlineshopping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void update(Category category) {
        categoryRepo.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepo.delete(category);
    }
}
