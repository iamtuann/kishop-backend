package kidev.vn.onlineshopping.model.cart;

import kidev.vn.onlineshopping.common.Priceable;
import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.entity.OrderItem;
import kidev.vn.onlineshopping.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemBasic implements Priceable {
    private Long id;
    private Long detailId;
    private Long price;
    private Long oldPrice;
    private Long totalPrice;
    private Long totalOldPrice;
    private Integer quantity;

    // data from ProductDetail (not auth)
    public ItemBasic(ProductDetail productDetail, Integer quantity) {
        this.detailId = productDetail.getId();
        this.quantity = quantity;
        this.price = productDetail.getPrice();
        this.oldPrice = productDetail.getOldPrice();
        this.totalPrice = this.price * this.quantity;
        this.totalOldPrice = this.oldPrice * this.quantity;
    }

    // data from CartItem (auth)
    public ItemBasic(CartItem cartItem) {
        this.id = cartItem.getId();
        this.detailId = cartItem.getProductDetail().getId();
        this.quantity = cartItem.getQuantity();
        this.price = cartItem.getPrice();
        this.oldPrice = cartItem.getOldPrice();
        this.totalPrice = this.price * this.quantity;
        this.totalOldPrice = this.oldPrice * this.quantity;
    }

    public ItemBasic(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.detailId = orderItem.getProductDetail().getId();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
        this.oldPrice = orderItem.getOldPrice();
        this.totalPrice = orderItem.getTotalPrice();
        this.totalOldPrice = orderItem.getTotalOldPrice();
    }
}
