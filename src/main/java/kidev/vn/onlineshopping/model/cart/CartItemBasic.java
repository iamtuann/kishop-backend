package kidev.vn.onlineshopping.model.cart;

import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemBasic {
    private Long id;
    private Long detailId;
    private Long price;
    private Long oldPrice;
    private Long totalPrice;
    private Long totalOldPrice;
    private Integer quantity;

    // data from ProductDetail (not auth)
    public CartItemBasic(ProductDetail productDetail, Integer quantity) {
        this.detailId = productDetail.getId();
        this.quantity = quantity;
        this.price = productDetail.getPrice();
        this.oldPrice = productDetail.getOldPrice();
        this.totalPrice = this.price * this.quantity;
        this.totalOldPrice = this.oldPrice * this.quantity;
    }

    // data from CartItem (auth)
    public CartItemBasic(CartItem cartItem) {
        this.id = cartItem.getId();
        this.detailId = cartItem.getProductDetail().getId();
        this.quantity = cartItem.getQuantity();
        this.price = cartItem.getPrice();
        this.oldPrice = cartItem.getOldPrice();
        this.totalPrice = this.price * this.quantity;
        this.totalOldPrice = this.oldPrice * this.quantity;
    }
}
