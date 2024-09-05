package kidev.vn.onlineshopping.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING(0, "Đặt hàng thành công", "Đơn hàng đang chờ phê duyệt"),
    CONFIRMED(1, "Đã xác nhận", "Đơn hàng đang được chuẩn bị"),
    SHIPPING(2, "Đang vận chuyển", "Đơn hàng đã được bàn giao cho đơn vị vận chuyển"),
    COMPLETED(3, "Đã giao", "Giao hàng thành công"),
    CANCELED(4, "Đã huỷ", "Đã huỷ đơn hàng");

    private final int value;

    private final String title;

    private final String description;

    OrderStatus(int value, String title, String description) {
        this.value = value;
        this.title = title;
        this.description = description;
    }

    public static OrderStatus fromValue(int value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid order status value: " + value);
    }
}
