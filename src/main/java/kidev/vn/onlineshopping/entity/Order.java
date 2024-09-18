package kidev.vn.onlineshopping.entity;

import kidev.vn.onlineshopping.enums.OrderStatus;
import kidev.vn.onlineshopping.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Nullable
    private AuthUser authUser;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "receive_date")
    private Date receiveDate;

    @Column(name = "order_code")
    private String orderCode;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<OrderItem> orderItems;

    @Column(name = "province")
    private String province;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "note")
    private String note;

    @Column(name = "shipping_fee")
    private Long shippingFee;

    @Column(name = "subtotal_price")
    private Long subTotalPrice;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "payment_type")
    private Integer paymentType;

    @Column(name = "payment_status")
    private Integer paymentStatus;

    @Column(name = "order_status")
    private Integer orderStatus;

    @Column(name = "shipping_status")
    private Integer shippingStatus;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<OrderHistory> orderHistories;

    public PaymentType getPaymentType() {
        return PaymentType.fromValue(this.paymentType);
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType.getValue();
    }

    public OrderStatus getOrderStatus() {
        return OrderStatus.fromValue(this.orderStatus);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus.getValue();
    }
}
