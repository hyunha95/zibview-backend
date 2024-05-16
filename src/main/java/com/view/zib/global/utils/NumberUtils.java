package com.view.zib.global.utils;

import org.springframework.stereotype.Component;

@Component
public class NumberUtils {

    // zero pad a number
    public String zeroPadNumber(int number, int length) {
        return String.format("%0" + length + "d", number);
    }
}
