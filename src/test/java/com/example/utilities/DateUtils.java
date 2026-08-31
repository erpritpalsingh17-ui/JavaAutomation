package com.example.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateUtils {
    private static final DateTimeFormatter ERAIL_DATE = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH);

    private DateUtils() {
    }

    public static LocalDate thirtyDaysFromToday() {
        return LocalDate.now().plusDays(30);
    }

    public static String formatForERail(LocalDate date) {
        return ERAIL_DATE.format(date);
    }
}
