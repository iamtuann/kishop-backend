package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.CartItem;
import kidev.vn.onlineshopping.entity.Order;
import kidev.vn.onlineshopping.model.order.OrderDetail;
import kidev.vn.onlineshopping.model.order.OrderModel;
import kidev.vn.onlineshopping.model.order.OrderShippingInfo;

import java.util.List;

public interface OrderService {
    Order findOne(Long id);

    Order create(OrderShippingInfo addressInfo, List<CartItem> cartItems, Long userId);

    Order createWithoutAuth(OrderShippingInfo addressInfo, List<CartItem> cartItems);

    List<OrderModel> getOrderModelsByUserId(Long userId);

    OrderDetail getOrderDetailByOrderCodeAndUserId(String orderCode, Long userId);
}
