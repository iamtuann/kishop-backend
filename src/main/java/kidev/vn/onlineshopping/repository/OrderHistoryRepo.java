package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "orderHistoryRepo")
public interface OrderHistoryRepo extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> getOrderHistoriesByOrderId(Long id);
}
