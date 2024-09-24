package kidev.vn.onlineshopping.model.product;

import kidev.vn.onlineshopping.entity.ProductVariant;
import lombok.Data;

@Data
public class ProductVariantResponse {
    private Long id;
    private String productName;
    private String name;
    private String color;
    private Long price;
    private Long oldPrice;

    private String thumbnail;

    public ProductVariantResponse(ProductVariant productVariant) {
        this.id = productVariant.getId();
        this.productName = productVariant.getProduct().getName();
        this.name = productVariant.getName();
        this.price = productVariant.getPrice();
        this.oldPrice = productVariant.getOldPrice();
        this.thumbnail = productVariant.getImageUrl();
    }
}
