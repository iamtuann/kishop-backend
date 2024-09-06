package kidev.vn.onlineshopping.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING(0),
    CONFIRMED(1),
    SHIPPING(2),
    COMPLETED(3),
    CANCELED(4);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
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
