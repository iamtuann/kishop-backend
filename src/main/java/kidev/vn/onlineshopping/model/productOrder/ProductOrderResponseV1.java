package kidev.vn.onlineshopping.model.productOrder;

import kidev.vn.onlineshopping.entity.ProductDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductOrderResponseV1 {
    private Long quantityId;
    private Long totalPrice;
    private Long totalOldPrice;
    private Integer quantityOrder;

    public ProductOrderResponseV1(ProductDetail productDetail, Integer quantity) {
        this.quantityId = productDetail.getId();
        this.quantityOrder = quantity;
        this.totalPrice = productDetail.getProductVariant().getPrice() * this.quantityOrder;
        this.totalOldPrice = productDetail.getProductVariant().getOldPrice() * this.quantityOrder;
    }
}
