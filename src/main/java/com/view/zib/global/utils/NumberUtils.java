package com.view.zib.global.utils;

import org.springframework.stereotype.Component;

@Component
public class NumberUtils {

    // zero pad a number
    public String zeroPadNumber(int number, int length) {
        return String.format("%0" + length + "d", number);
    }

    public static boolean isDigit(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
