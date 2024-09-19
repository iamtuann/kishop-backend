package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.config.cloud.CloudinaryService;
import kidev.vn.onlineshopping.entity.ProductImage;
import kidev.vn.onlineshopping.entity.ProductVariant;
import kidev.vn.onlineshopping.repository.ProductImageRepo;
import kidev.vn.onlineshopping.service.ProductImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepo productImageRepo;

    private final CloudinaryService cloudinaryService;

    private final String PRODUCT_IMAGE_FOLDER = "products/images";

    @Override
    public ProductImage findOne(Long id) {
        return productImageRepo.getProductImageById(id);
    }

    @Override
    public List<ProductImage> saveAll(ProductVariant productVariant, MultipartFile[] files) throws IOException {
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile image : files) {
            ProductImage productImage = new ProductImage();
            Map data = cloudinaryService.uploadFile(image, PRODUCT_IMAGE_FOLDER);
            productImage.setUrl((String) data.get("url"));
            productImage.setPathUrl(PRODUCT_IMAGE_FOLDER + "/" + data.get("display_name"));
            productImage.setProductVariant(productVariant);
            productImages.add(productImage);
        }
        return productImageRepo.saveAll(productImages);
    }

    @Override
    public void create(ProductImage productImage) {
        productImageRepo.save(productImage);
    }

    @Override
    public void update(ProductImage productImage) {
        productImageRepo.save(productImage);
    }

    @Override
    public void delete(ProductImage productImage) {
        productImageRepo.delete(productImage);
    }
}
