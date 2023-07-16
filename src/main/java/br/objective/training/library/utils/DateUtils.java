package br.objective.training.library.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtils {
    public static LocalDate addDays(LocalDate date, int days){
        return date.plusDays(days);
    }

    public static LocalDate addDaysSkipWeekends(LocalDate date, int days) {
        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < days) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++addedDays;
            }
        }

        return result;
    }
}
