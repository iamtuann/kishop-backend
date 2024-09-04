package kidev.vn.onlineshopping.model.cart;

import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.entity.ProductDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CartItemDetail extends ItemDetail {
    public CartItemDetail(ProductDetail pd, Integer quantity) {
        super(pd, quantity);
    }

    public CartItemDetail(CartItem cartItem) {
        super(cartItem);
    }
}
