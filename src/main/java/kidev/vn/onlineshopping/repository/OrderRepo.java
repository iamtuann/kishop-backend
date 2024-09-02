package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "orderRepo")
public interface OrderRepo extends JpaRepository<Order, Long> {
    Order getOrderById(Long id);

    Order getOrderByOrderCode(String orderCode);

    List<Order> getAllByAuthUserId(Long id);
}
