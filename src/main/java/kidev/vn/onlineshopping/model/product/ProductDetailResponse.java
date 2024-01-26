package kidev.vn.onlineshopping.model.product;

import kidev.vn.onlineshopping.entity.ProductDetail;
import lombok.Data;

@Data
public class ProductDetailResponse {
    private Long id;
    private String productName;
    private String name;
    private Long price;
    private Long offPrice;

    private String thumbnail;

    public ProductDetailResponse(ProductDetail productDetail) {
        this.id = productDetail.getId();
        this.productName = productDetail.getProduct().getName();
        this.name = productDetail.getName();
        this.price = productDetail.getPrice();
        this.offPrice = productDetail.getOffPrice();
        this.thumbnail = productDetail.getProductImages().get(0).getUrl();
    }
}
