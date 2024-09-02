package kidev.vn.onlineshopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
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
    private Long subtotalPrice;

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
}
