package kidev.vn.onlineshopping.enums;

import lombok.Getter;

@Getter
public enum UserGender {
    MALE(0),
    FEMALE(1),
    OTHER(2);

    private final int value;

    UserGender(int value) {
        this.value = value;
    }

    public static UserGender fromValue(int value) {
        for (UserGender gender : UserGender.values()) {
            if (gender.getValue() == value) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender value: " + value);
    }
}
