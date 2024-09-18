package kidev.vn.onlineshopping.model.order;

import kidev.vn.onlineshopping.entity.OrderItem;
import kidev.vn.onlineshopping.model.cart.ItemDetail;

public class OrderItemDetail extends ItemDetail {
    public OrderItemDetail(OrderItem orderItem) {
        super(orderItem);
    }
}
