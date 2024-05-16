package com.view.zib.global.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberUtilsTest {

    @Test
    void zeroPadNumber() {
        NumberUtils numberUtils = new NumberUtils();
        assertEquals("01", numberUtils.zeroPadNumber(1, 2));
        assertEquals("09", numberUtils.zeroPadNumber(9, 2));
        assertEquals("12", numberUtils.zeroPadNumber(12, 2));
    }
}