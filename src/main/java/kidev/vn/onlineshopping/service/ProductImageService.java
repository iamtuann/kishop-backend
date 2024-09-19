package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.ProductImage;
import kidev.vn.onlineshopping.entity.ProductVariant;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductImageService {
    ProductImage findOne(Long id);

    List<ProductImage> saveAll(ProductVariant productVariant, MultipartFile[] files) throws IOException;

    void create(ProductImage productImage);

    void update(ProductImage productImage);

    void delete(ProductImage productImage);
}
