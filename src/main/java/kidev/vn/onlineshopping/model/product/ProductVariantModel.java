package kidev.vn.onlineshopping.model.product;

import kidev.vn.onlineshopping.entity.ProductImage;
import kidev.vn.onlineshopping.entity.ProductDetail;
import kidev.vn.onlineshopping.entity.ProductVariant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductVariantModel {
    private Long id;
    private String productName;
    private String name;
    private String color;
    private Long price;
    private Long oldPrice;
    private Integer status;
    private String previewImage;
    private List<String> imageUrls;
    private List<ProductDetailModel> productDetails;

    public ProductVariantModel(ProductVariant p) {
        this.id = p.getId();
        this.productName = p.getProduct().getName();
        this.name = p.getName();
        this.color = p.getColor().getName();
        this.price = p.getPrice();
        this.oldPrice = p.getOldPrice();
        this.status = p.getStatus();
        this.previewImage = p.getImageUrl();
        this.imageUrls = new ArrayList<>();
        for (ProductImage image : p.getProductImages()) {
            this.imageUrls.add(image.getUrl());
        }
        this.productDetails = new ArrayList<>();
        for (ProductDetail pq : p.getProductDetails()) {
            this.productDetails.add(new ProductDetailModel(pq));
        }
    }
}
