package edu.ucsd.cse110.successorator.data.db;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class LocalDateConverter {
    @TypeConverter
    public static LocalDate toLocalDate(String date) {
        return date == null ? null : LocalDate.parse(date);
    }

    @TypeConverter
    public static String toString(LocalDate date) {
        return date == null ? null : date.toString();
    }
}