package kidev.vn.onlineshopping.enums;

import lombok.Getter;

@Getter
public enum PaymentType {
    COD(1),
    BANKING(2);

    private final int value;

    PaymentType(int value) {
        this.value = value;
    }

    public static PaymentType fromValue(int value) {
        for (PaymentType type : PaymentType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid payment type value: " + value);
    }
}
