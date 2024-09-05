package kidev.vn.onlineshopping.model.order;

import kidev.vn.onlineshopping.entity.Order;
import kidev.vn.onlineshopping.enums.OrderStatus;
import kidev.vn.onlineshopping.enums.PaymentType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderModel {
    private String receiverName;
    private String phoneNumber;
    private Date orderDate;
    private Date receiveDate;
    private String orderCode;
    private List<OrderItemDetail> orderItemDetails;
    private String province;
    private String district;
    private String ward;
    private String detailAddress;
    private String note;
    private Long shippingFee;
    private Long subTotalPrice;
    private Long totalPrice;
    private PaymentType paymentType;
    private OrderStatus orderStatus;

    public OrderModel(Order order) {
        this.receiverName = order.getReceiverName();
        this.phoneNumber = order.getPhoneNumber();
        this.orderDate = order.getOrderDate();
        this.receiveDate = order.getReceiveDate();
        this.orderCode = order.getOrderCode();
        this.orderItemDetails = order.getOrderItems().stream()
                .map(OrderItemDetail::new)
                .collect(Collectors.toList());
        this.province = order.getProvince();
        this.district = order.getDistrict();
        this.ward = order.getWard();
        this.detailAddress = order.getDetailAddress();
        this.note = order.getNote();
        this.shippingFee = order.getShippingFee();
        this.subTotalPrice = order.getSubTotalPrice();
        this.totalPrice = order.getTotalPrice();
        this.paymentType = order.getPaymentType();
        this.orderStatus = order.getOrderStatus();
    }
}
