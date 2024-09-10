package kidev.vn.onlineshopping.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    NOT_VERIFY(0),
    VERIFY(1);

    private final int value;

    UserStatus(int value) {
        this.value = value;
    }

    public static UserStatus fromValue(int value) {
        for (UserStatus status : UserStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid gender value: " + value);
    }
}
