package kr.kro.photoliner.common.util.datetime;

import java.time.LocalDateTime;

public class DateTimes {

    private DateTimes() {
    }

    public static boolean isBetween(LocalDateTime start, LocalDateTime end, LocalDateTime target) {
        return isAfterEqualsTo(target, start) && isBeforeEqualsTo(target, end);
    }

    public static boolean isAfterEqualsTo(LocalDateTime date, LocalDateTime standardDate) {
        return !date.isBefore(standardDate);
    }

    public static boolean isBeforeEqualsTo(LocalDateTime date, LocalDateTime standardDate) {
        return !date.isAfter(standardDate);
    }
}
