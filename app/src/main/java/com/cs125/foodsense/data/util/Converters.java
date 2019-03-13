package com.cs125.foodsense.data.util;

import android.arch.persistence.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Converters {
    //private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @TypeConverter
    public static LocalDateTime toDate(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            return LocalDateTime.parse(dateString);
        }
    }

    @TypeConverter
    public static String toDateString(LocalDateTime date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }
}
