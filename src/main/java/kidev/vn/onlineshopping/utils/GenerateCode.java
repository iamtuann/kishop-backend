package kidev.vn.onlineshopping.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GenerateCode {
    public static String generateOrderCode(Long orderId) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return "DH" + currentDate + orderId;
    }
}
