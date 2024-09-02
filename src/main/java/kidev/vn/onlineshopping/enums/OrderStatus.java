package kidev.vn.onlineshopping.enums;

public enum OrderStatus {
    PENDING(0),
    CONFIRMED(1),
    COMPLETED(2),
    CANCELED(3);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
