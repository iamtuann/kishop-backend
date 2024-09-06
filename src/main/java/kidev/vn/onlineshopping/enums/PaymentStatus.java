package kidev.vn.onlineshopping.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    NOT_PAID(0),
    PAID(1);

    private final int value;

    PaymentStatus(int value) {
        this.value = value;
    }
}
