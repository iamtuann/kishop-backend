package kidev.vn.onlineshopping.model.productOrder;

import kidev.vn.onlineshopping.entity.ProductQuantity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductOrderResponseV1 {
    private Long quantityId;
    private Long totalPrice;
    private Long totalOldPrice;
    private Integer quantityOrder;

    public ProductOrderResponseV1(ProductQuantity productQuantity, Integer quantity) {
        this.quantityId = productQuantity.getId();
        this.quantityOrder = quantity;
        this.totalPrice = productQuantity.getProductVariant().getPrice() * this.quantityOrder;
        this.totalOldPrice = productQuantity.getProductVariant().getOldPrice() * this.quantityOrder;
    }
}
