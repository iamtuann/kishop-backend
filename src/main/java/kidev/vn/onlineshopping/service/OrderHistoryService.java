package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Order;
import kidev.vn.onlineshopping.entity.OrderHistory;
import kidev.vn.onlineshopping.model.order.OrderHistoryModel;

import java.util.List;

public interface OrderHistoryService {
    List<OrderHistoryModel> getOrderHistoriesByOrderId(Long id);

    void create(OrderHistory orderHistory);

    void create(Order order);
}
