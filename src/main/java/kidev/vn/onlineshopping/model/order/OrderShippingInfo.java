package kidev.vn.onlineshopping.model.order;

import kidev.vn.onlineshopping.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderShippingInfo {
    private String receiverName;
    private String phoneNumber;
    private String province;
    private String district;
    private String ward;
    private String detailAddress;
    private String note;
    private PaymentType paymentType;
}
