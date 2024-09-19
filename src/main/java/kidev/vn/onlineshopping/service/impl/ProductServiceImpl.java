package kidev.vn.onlineshopping.service.impl;

import com.github.slugify.Slugify;
import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.entity.Category;
import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.entity.ProductVariant;
import kidev.vn.onlineshopping.entity.Size;
import kidev.vn.onlineshopping.model.product.*;
import kidev.vn.onlineshopping.repository.ProductRepo;
import kidev.vn.onlineshopping.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final ProductVariantService productVariantService;

    private final Slugify slg = new Slugify();

    private final BrandService brandService;

    private final CategoryService categoryService;

    private final SizeService sizeService;

    private final ColorService colorService;


    @Override
    public Product findOne(Long id) {
        return productRepo.getProductById(id);
    }

    @Override
    public Product saveProduct(ProductRequest productRequest) {
        Product product;
        if (productRequest.getId() != null) {
            product = productRepo.getProductById(productRequest.getId());
        } else {
            product = new Product();
            product.setStatus(Constants.StatusProduct.NOTSELL);
            product.setCreatedDate(new Date());
        }
        if (productRequest.getProductVariantId() != null) {
            product.setProductPreview(productVariantService.findOne(productRequest.getProductVariantId()));
        }
        if (productRequest.getStatus() != null) {
            product.setStatus(productRequest.getStatus());
        }
        product.setUpdatedDate(new Date());
        product.setName(productRequest.getName());
        product.setSlug(slg.slugify(productRequest.getName()));
        product.setDescription(productRequest.getDescription());
        product.setBrand(brandService.findOne(productRequest.getBrandId()));
        List<Category> categories = new ArrayList<>();
        for (Long id : productRequest.getCategoryIds()) {
            categories.add(categoryService.findOne(id));
        }
        product.setCategories(categories);
        List<Size> sizes = new ArrayList<>();
        for (Long id : productRequest.getSizeIds()) {
            sizes.add(sizeService.findOne(id));
        }
        product.setSizes(sizes);
        return productRepo.save(product);
    }

    private Boolean isFiltering(List<String> categories, List<String> brandNames,
                                List<String> sizes, List<String> colors,
                                List<String> genders, Boolean sale) {
        return categories != null && !categories.isEmpty() ||
                brandNames != null && !brandNames.isEmpty() ||
                sizes != null && !sizes.isEmpty() ||
                colors != null && !colors.isEmpty() ||
                genders != null && !genders.isEmpty() ||
                sale != null && sale;
    }

    @Override
    public Page<ProductBasicModel> searchProduct(String name,
                                                 List<String> categories, List<String> brandNames,
                                                 List<String> sizes, List<String> colors, List<String> genders,
                                                 Boolean sale, Pageable pageable) {

        Boolean isFiltering = isFiltering(categories, brandNames, sizes, colors, genders, sale);
        Page<ProductVariant> productVariants = productVariantService.searchProduct(name, categories, brandNames, sizes, colors, genders, sale, pageable);
        ArrayList<ProductBasicModel> productBasicModels = new ArrayList<>();
        for (ProductVariant pv : productVariants) {
            ProductBasicModel model = new ProductBasicModel(pv, isFiltering);
            productBasicModels.add(model);
        }
        return new PageImpl<>(productBasicModels, pageable, productVariants.getTotalElements());
    }

    @Override
    public ProductModel getProductSellingBySlug(String slug) {
        Product product = productRepo.getProductBySlugAndStatus(slug, Constants.StatusProduct.SELLING);
        if (product != null) {
            return new ProductModel(product);
        } else {
            return null;
        }
    }


    @Override
    public void create(Product product) {
        productRepo.save(product);
    }

    @Override
    public Product update(Product product, ProductVariantRequest variantRequest) throws IOException {
        for (ProductVariantRequestModel model : variantRequest.getModels()) {
            productVariantService.saveProductVariant(model, product);
        }
        product.setColors(colorService.getColorsByColorNames(variantRequest.getListColor()));
        return productRepo.save(product);
    }

    @Override
    public void update(List<ProductDetailRequest> detailRequests) {
        for (ProductDetailRequest detail : detailRequests) {
            productVariantService.update(detail);
        }
    }

    @Override
    public Product update(Product product) {
        return productRepo.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepo.delete(product);
    }
}
