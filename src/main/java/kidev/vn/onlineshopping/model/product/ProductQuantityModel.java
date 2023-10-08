package kidev.vn.onlineshopping.model.product;

import kidev.vn.onlineshopping.entity.ProductQuantity;
import kidev.vn.onlineshopping.entity.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductQuantityModel {
    private Long id;
    private Size size;
    private Integer quantity;

    public ProductQuantityModel(ProductQuantity pq) {
        this.id = pq.getId();
        this.size = pq.getSize();
        this.quantity = pq.getQuantity();
    }
}
