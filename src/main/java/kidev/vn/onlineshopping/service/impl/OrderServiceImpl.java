package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Order;
import kidev.vn.onlineshopping.repository.OrderRepo;
import kidev.vn.onlineshopping.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;

    @Override
    public Order findOne(Long id) {
        return orderRepo.getOrderById(id);
    }
}
