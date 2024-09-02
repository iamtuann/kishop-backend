package kidev.vn.onlineshopping.model.order;

import kidev.vn.onlineshopping.model.cart.CartItemDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentInfo {
    private final Long MAX_PRICE_FREE_SHIPPING = 2000000L;
    private final Long SHIPPING_FEE = 40000L;
    private List<CartItemDetail> cartItemDetails;
    private Long subTotalPrice;
    private Long shippingFee;
    private Long totalPrice;

    public OrderPaymentInfo(List<CartItemDetail> cartItemDetails) {
        this.cartItemDetails = cartItemDetails;
        this.subTotalPrice = cartItemDetails.stream()
                .mapToLong(CartItemDetail::getPrice)
                .sum();
        this.shippingFee = subTotalPrice > MAX_PRICE_FREE_SHIPPING ? Long.valueOf(0L) : this.SHIPPING_FEE;
        this.totalPrice = this.subTotalPrice + this.shippingFee;
    }
}
