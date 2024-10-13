package com.view.zib.global.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import java.util.Calendar;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class DateUtils {

    public static final String DATE_FORMAT_FOR_VWORLD = "yyyyMM";
    public static final LocalDate START_DATE_FOR_VWORLD = LocalDate.of(LocalDate.now().getYear() - 3, 1, 1);
    public static final DateTimeFormatter DATE_FORMATTER_FOR_VWORLD = new DateTimeFormatterBuilder()
            .appendPattern(DATE_FORMAT_FOR_VWORLD)
            .toFormatter();

    public static Set<String> generateYearMonthRangeFrom2000(LocalDate now) {
        return START_DATE_FOR_VWORLD.datesUntil(now, Period.ofMonths(1))
                .map(date -> date.format(DATE_FORMATTER_FOR_VWORLD))
                .collect(Collectors.toSet());
    }

    public static Set<String> generateYearMonthRangeFrom(LocalDate now, int fromYear) {
        return LocalDate.of(fromYear, 1, 1).datesUntil(now, Period.ofMonths(1))
                .map(date -> date.format(DATE_FORMATTER_FOR_VWORLD))
                .collect(Collectors.toSet());
    }

}
