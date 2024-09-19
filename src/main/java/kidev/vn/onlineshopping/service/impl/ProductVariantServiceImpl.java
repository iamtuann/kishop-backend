package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.config.cloud.CloudinaryService;
import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.entity.ProductImage;
import kidev.vn.onlineshopping.entity.ProductVariant;
import kidev.vn.onlineshopping.model.product.ProductDetailRequest;
import kidev.vn.onlineshopping.model.product.ProductDetailRequestModel;
import kidev.vn.onlineshopping.model.product.ProductVariantRequestModel;
import kidev.vn.onlineshopping.repository.ProductVariantRepo;
import kidev.vn.onlineshopping.service.ProductDetailService;
import kidev.vn.onlineshopping.service.ProductImageService;
import kidev.vn.onlineshopping.service.ProductVariantService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepo productVariantRepo;

    private final CloudinaryService cloudinaryService;

    private final ProductImageService productImageService;

    private final ProductDetailService productDetailService;

    public final String PRODUCT_IMAGE_FOLDER = "products/images";

    @Override
    public ProductVariant findOne(Long id) {
        return productVariantRepo.getProductVariantById(id);
    }

    @Override
    public Page<ProductVariant> searchProduct(String name, List<String> categories, List<String> brandNames, List<String> sizes, List<String> colors, List<String> genders, Boolean sale, Pageable pageable) {
        return productVariantRepo.searchProduct(name, categories, brandNames, sizes, colors, genders, sale, pageable);
    }

    @Override
    public void saveProductVariant(ProductVariantRequestModel model, Product product) throws IOException {
        ProductVariant pv;
        if (model.getId() != null) {
            pv = productVariantRepo.getProductVariantById(model.getId());
        } else {
            pv = new ProductVariant();
        }
        pv.setProduct(product);
        pv.setName(model.getName());
        pv.setStatus(model.getStatus());
        pv.setPrice(model.getPrice());
        if (model.getOldPrice() != null) {
            pv.setOldPrice(model.getOldPrice());
        } else {
            pv.setOldPrice(model.getPrice());
        }
        Map data = cloudinaryService.uploadFile(model.getPreviewImage(), PRODUCT_IMAGE_FOLDER);
        pv.setImageUrl((String) data.get("url"));
        pv.setImagePath(PRODUCT_IMAGE_FOLDER + "/" + data.get("display_name"));
        pv = productVariantRepo.save(pv);

        List<ProductImage> productImages = productImageService.saveAll(pv, model.getImages());
        pv.setProductImages(productImages);
        productVariantRepo.save(pv);
    }

    @Override
    public void create(ProductVariant ProductVariant) {
        productVariantRepo.save(ProductVariant);
    }

    @Override
    public void update(ProductVariant ProductVariant) {
        productVariantRepo.save(ProductVariant);
    }

    @Override
    public void update(ProductDetailRequest detailRequest) {
        ProductVariant pv = productVariantRepo.getProductVariantById(detailRequest.getProductVariantId());
        for (ProductDetailRequestModel model : detailRequest.getModels()) {
            productDetailService.update(pv, model);
        }
    }

    @Override
    public void delete(ProductVariant ProductVariant) {
        productVariantRepo.delete(ProductVariant);
    }
}
