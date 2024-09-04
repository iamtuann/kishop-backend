package kidev.vn.onlineshopping.entity;

import kidev.vn.onlineshopping.common.Priceable;
import kidev.vn.onlineshopping.model.cart.ItemBasic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
public class OrderItem implements Priceable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Long price;

    @Column(name = "old_price")
    private Long oldPrice;

    public OrderItem(CartItem cartItem, Order order) {
        this.productDetail = cartItem.getProductDetail();
        this.quantity = cartItem.getQuantity();
        this.price = this.productDetail.getPrice();
        this.oldPrice = this.productDetail.getOldPrice();
        this.order = order;
    }

    @Override
    public Long getTotalPrice() {
        return this.price * this.quantity;
    }

    @Override
    public Long getTotalOldPrice() {
        return this.oldPrice * this.quantity;
    }
}
