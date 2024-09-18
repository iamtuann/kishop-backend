package kidev.vn.onlineshopping.model.order;

import kidev.vn.onlineshopping.model.cart.CartItemRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private OrderShippingInfo shippingInfo;
    private List<CartItemRequest> products;
}
