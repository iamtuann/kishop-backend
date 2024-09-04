package kidev.vn.onlineshopping.model.order;

import kidev.vn.onlineshopping.model.cart.ItemDetail;
import kidev.vn.onlineshopping.utils.CalculatePrice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentInfo {
    private List<? extends ItemDetail> itemDetails;
    private Long subTotalPrice;
    private Long shippingFee;
    private Long totalPrice;

    public OrderPaymentInfo(List<? extends ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
        this.subTotalPrice = CalculatePrice.totalPrice(itemDetails);
        this.shippingFee = CalculatePrice.getShippingFee(this.subTotalPrice);
        this.totalPrice = this.subTotalPrice + this.shippingFee;
    }
}
