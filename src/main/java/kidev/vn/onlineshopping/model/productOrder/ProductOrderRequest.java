package kidev.vn.onlineshopping.model.productOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderRequest {
    private Long productQtyId;
    private Integer quantity;
}
