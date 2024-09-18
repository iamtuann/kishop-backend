package kidev.vn.onlineshopping.model.order;

import kidev.vn.onlineshopping.entity.OrderHistory;
import kidev.vn.onlineshopping.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryModel {
    private Long id;
    private Long orderId;
    private Date date;
    private String title;
    private String description;
    private OrderStatus orderStatus;

    public OrderHistoryModel(OrderHistory orderHistory) {
        this.id = orderHistory.getId();
        this.orderId = orderHistory.getOrder().getId();
        this.date = orderHistory.getDate();
        this.title = orderHistory.getTitle();
        this.description = orderHistory.getDescription();
        this.orderStatus = orderHistory.getOrderStatus();
    }
}
