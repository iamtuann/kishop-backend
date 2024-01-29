package kidev.vn.onlineshopping.controller;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.entity.Category;
import kidev.vn.onlineshopping.model.CommonResponse;
import kidev.vn.onlineshopping.model.category.CategoryModel;
import kidev.vn.onlineshopping.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    public static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public CommonResponse<List<CategoryModel>> getAllCategories() {
        CommonResponse<List<CategoryModel>> response = new CommonResponse<>();
        try {
            List<CategoryModel> output = categoryService.findAll();
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setOutput(output);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Có lỗi xảy ra");
            logger.error("getAllCategories", e);
        }
        return response;
    }

    @PostMapping("/create")
    public CommonResponse<CategoryModel> createCategory(@RequestBody CategoryModel request) {
        CommonResponse<CategoryModel> response = new CommonResponse<>();
        try {
            Category category = new Category();
            category.setName(request.getName());
            category.setDescription(request.getDescription());
            category.setType(0);
            CategoryModel output = categoryService.saveCategory(category);
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setOutput(output);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Có lỗi xảy ra");
            logger.error("createCategory", e);
        }
        return response;
    }
}
