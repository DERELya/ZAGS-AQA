package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class HelperForDate {
    private HelperForDate() {
    }

    public static String toIsoDate(LocalDate date) {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    public static String toIsoDateFromTimestamp(String dateTime) {
        return dateTime.substring(0, 10);
    }
}
