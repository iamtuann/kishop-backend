package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.model.product.ProductBasicModel;
import kidev.vn.onlineshopping.model.product.ProductDetailModel;
import kidev.vn.onlineshopping.model.product.ProductModel;
import kidev.vn.onlineshopping.repository.ProductRepo;
import kidev.vn.onlineshopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepo productRepo;


    @Override
    public Product findOne(Long id) {
        return productRepo.getProductById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Page<ProductBasicModel> searchProduct(String name,
                                                 List<String> categoryIds, String brandId,
                                                 String sizeId, String colorId,
                                                 Boolean sale, Pageable pageable) {

        Page<Product> productPage = productRepo.searchProduct(name, categoryIds, brandId, sizeId, colorId, sale, pageable);
        List<ProductBasicModel> productBasicModels = new ArrayList<>();
        for (Product product : productPage) {
            ProductBasicModel model = new ProductBasicModel(product);
            productBasicModels.add(model);
        }
        PageImpl<ProductBasicModel> response = new PageImpl<>(productBasicModels, pageable, productPage.getTotalElements());
        return response;
    }

    @Override
    public ProductModel getProductSellingtBySlug(String slug) {
        Product product = productRepo.getProductBySlugAndStatus(slug, Constants.StatusProduct.SELLING);
        ProductModel productModel = new ProductModel(product);
        return productModel;
    }

    @Override
    public List<ProductBasicModel> getTopProductByCreatedDate(int number) {
        List<Product> products = productRepo.findLatestProducts(number);
        List<ProductBasicModel> productBasicModels = new ArrayList<>();
        if (products != null || products.size() != 0) {
            for (Product p : products) {
                productBasicModels.add(new ProductBasicModel(p));
            }
        }
        return productBasicModels;
    }

    @Override
    public void create(Product product) {
        productRepo.save(product);
    }

    @Override
    public void update(Product product) {
        productRepo.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepo.delete(product);
    }
}
