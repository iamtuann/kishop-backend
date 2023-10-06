package kidev.vn.onlineshopping.model.product;

import kidev.vn.onlineshopping.entity.ProductDetail;
import lombok.Data;

@Data
public class ProductDetailResponse {
    private Long id;
    private String name;
    private String color;
    private Long price;
    private Long offPrice;

    private String thumbnail;

    public ProductDetailResponse(ProductDetail productDetail) {
        this.id = productDetail.getId();
        this.name = productDetail.getProduct().getName();
        this.color = productDetail.getColor().getName();
        this.price = productDetail.getPrice();
        this.offPrice = productDetail.getOffPrice();
        this.thumbnail = productDetail.getProductImages().get(0).getUrl();
    }
}
