package kidev.vn.onlineshopping.service.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.entity.ProductDetail;
import kidev.vn.onlineshopping.model.product.ProductBasicModel;
import kidev.vn.onlineshopping.model.product.ProductModel;
import kidev.vn.onlineshopping.repository.ProductDetailRepo;
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
    @Autowired
    ProductDetailRepo productDetailRepo;


    @Override
    public Product findOne(Long id) {
        return productRepo.getProductById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    private Boolean isFiltering(List<String> categories, List<String> brandNames,
                                List<String> sizes, List<String> colors,
                                List<String> genders, Boolean sale) {
        if (categories!=null && !categories.isEmpty() ||
                brandNames!=null && !brandNames.isEmpty() ||
                sizes!=null && !sizes.isEmpty() ||
                colors!=null && !colors.isEmpty() ||
                genders!=null && !genders.isEmpty() ||
                sale!=null && sale == true
        ) return true;
        else return false;
    }

    @Override
    public Page<ProductBasicModel> searchProduct(String name,
                                                 List<String> categories, List<String> brandNames,
                                                 List<String> sizes, List<String> colors, List<String> genders,
                                                 Boolean sale, Pageable pageable) {

//        Page<Product> productPage = productRepo.searchProduct(name, categories, brandNames, sizes, colors, genders, sale, pageable);
//        List<ProductBasicModel> productBasicModels = new ArrayList<>();
//        for (Product product : productPage) {
//            ProductBasicModel model = new ProductBasicModel(product);
//            productBasicModels.add(model);
//        }
        Boolean isFiltering = isFiltering(categories, brandNames, sizes, colors, genders, sale);
        Page<ProductDetail> productDetails = productDetailRepo.searchProduct(name, categories, brandNames, sizes, colors, genders, sale, pageable);
        ArrayList<ProductBasicModel> productBasicModels = new ArrayList<>();
        for (ProductDetail pd : productDetails) {
            ProductBasicModel model = new ProductBasicModel(pd, isFiltering);
            productBasicModels.add(model);
        }
        PageImpl<ProductBasicModel> response = new PageImpl<>(productBasicModels, pageable, productDetails.getTotalElements());
        return response;
    }

    @Override
    public ProductModel getProductSellingBySlug(String slug) {
        Product product = productRepo.getProductBySlugAndStatus(slug, Constants.StatusProduct.SELLING);
        if (product != null) {
            ProductModel productModel = new ProductModel(product);
            return productModel;
        } else {
            return null;
        }
    }

    @Override
    public List<ProductBasicModel> getTopProductByCreatedDate(Pageable pageable) {
        Page<Product> products = productRepo.getTopProductByCreatedDate(pageable);
        List<ProductBasicModel> productBasicModels = new ArrayList<>();
        if (products != null || products.getSize() != 0) {
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
