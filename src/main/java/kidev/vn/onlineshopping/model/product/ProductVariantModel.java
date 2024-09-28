package kidev.vn.onlineshopping.model.product;

import kidev.vn.onlineshopping.entity.Color;
import kidev.vn.onlineshopping.entity.ProductDetail;
import kidev.vn.onlineshopping.entity.ProductVariant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductVariantModel {
    private Long id;
    private String productName;
    private String name;
    private List<String> colors;
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
        this.colors = p.getColors().stream().map(Color::getEngName).collect(Collectors.toList());
        this.price = p.getPrice();
        this.oldPrice = p.getOldPrice();
        this.status = p.getStatus();
        this.previewImage = p.getImageUrl();
        this.imageUrls = new ArrayList<>();
//        for (ProductImage image : p.getProductImages()) {
//            this.imageUrls.add(image.getUrl());
//        }
        this.productDetails = new ArrayList<>();
        for (ProductDetail pq : p.getProductDetails()) {
            this.productDetails.add(new ProductDetailModel(pq));
        }
    }
}
