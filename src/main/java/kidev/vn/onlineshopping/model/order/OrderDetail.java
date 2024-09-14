package kidev.vn.onlineshopping.model.order;

import kidev.vn.onlineshopping.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends OrderModel {
    List<OrderHistoryModel> orderHistories;

    public OrderDetail(Order order) {
        super(order);
        this.orderHistories = order.getOrderHistories()
                .stream().map(OrderHistoryModel::new).collect(Collectors.toList());
    }
}
