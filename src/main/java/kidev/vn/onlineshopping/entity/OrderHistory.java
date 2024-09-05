package kidev.vn.onlineshopping.entity;

import kidev.vn.onlineshopping.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_history")
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "date")
    private Date date;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "order_status")
    private Integer orderStatus;

    public OrderStatus getOrderStatus() {
        return OrderStatus.fromValue(this.orderStatus);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus.getValue();
    }

    public OrderHistory(Order order) {
        this.order = order;
        this.date = new Date();
        this.title = order.getOrderStatus().getTitle();
        this.description = order.getOrderStatus().getDescription();
        this.orderStatus = order.getOrderStatus().getValue();
    }
}
