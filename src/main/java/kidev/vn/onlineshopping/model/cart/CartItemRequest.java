package kidev.vn.onlineshopping.model.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {
    private Long id;
    private Long detailId;
    private Integer quantity;
}
