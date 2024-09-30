package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Product;
import kidev.vn.onlineshopping.entity.ProductVariant;
import kidev.vn.onlineshopping.model.productVariant.ProductVariantRequest;
import kidev.vn.onlineshopping.repository.ProductVariantRepo;
import kidev.vn.onlineshopping.service.ColorService;
import kidev.vn.onlineshopping.service.MediaService;
import kidev.vn.onlineshopping.service.ProductDetailService;
import kidev.vn.onlineshopping.service.ProductVariantService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepo productVariantRepo;

    private final MediaService mediaService;

    private final ProductDetailService productDetailService;

    private final ColorService colorService;

    public final String PRODUCT_IMAGE_FOLDER = "products/images";

    @Override
    public ProductVariant findOne(Long id) {
        return productVariantRepo.getProductVariantById(id);
    }

    @Override
    public Page<ProductVariant> searchProduct(String name, List<String> categories, List<String> brandNames, List<String> colors, List<String> genders, Boolean sale, Pageable pageable) {
        return productVariantRepo.searchProduct(name, categories, brandNames, colors, genders, sale, pageable);
    }

    @Override
    public void saveProductVariant(ProductVariantRequest model, Product product) {
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
        pv.setColors(colorService.getColorsByIds(model.getColorIds()));
        pv.setImagePreview(mediaService.getMediaById(model.getPreviewMediaId()));
        pv.setProductVariantMedia(mediaService.findAllByIds(model.getMediaIds()));
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
    public void delete(ProductVariant ProductVariant) {
        productVariantRepo.delete(ProductVariant);
    }
}
