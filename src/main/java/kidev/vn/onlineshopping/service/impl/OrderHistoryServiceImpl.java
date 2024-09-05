package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Order;
import kidev.vn.onlineshopping.entity.OrderHistory;
import kidev.vn.onlineshopping.model.order.OrderHistoryModel;
import kidev.vn.onlineshopping.repository.OrderHistoryRepo;
import kidev.vn.onlineshopping.service.OrderHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {
    private OrderHistoryRepo orderHistoryRepo;


    @Override
    public List<OrderHistoryModel> getOrderHistoriesByOrderId(Long id) {
        List<OrderHistory> orderHistories = orderHistoryRepo.getOrderHistoriesByOrderId(id);

        return orderHistories.stream()
                .map(OrderHistoryModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public void create(OrderHistory orderHistory) {
        orderHistoryRepo.save(orderHistory);
    }

    @Override
    public void create(Order order) {
        OrderHistory orderHistory = new OrderHistory(order);
        orderHistoryRepo.save(orderHistory);
    }
}
