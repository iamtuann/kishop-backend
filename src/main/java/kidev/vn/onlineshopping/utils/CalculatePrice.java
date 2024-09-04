package kidev.vn.onlineshopping.utils;

import kidev.vn.onlineshopping.common.Priceable;

import java.util.List;

public class CalculatePrice {
    public static long MAX_PRICE_FREE_SHIPPING = 2000000L;
    public static long SHIPPING_FEE = 40000L;

    public static Long totalPrice(List<? extends Priceable> items) {
        return items.stream()
                .mapToLong(Priceable::getTotalPrice)
                .sum();
    }

    public static Long totalOldPrice(List<? extends Priceable> items) {
        return items.stream()
                .mapToLong(Priceable::getTotalOldPrice)
                .sum();
    }

    public static Long getShippingFee(Long orderPrice) {
        return orderPrice > MAX_PRICE_FREE_SHIPPING ? 0L : SHIPPING_FEE;
    }
}
