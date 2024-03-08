package edu.ucsd.cse110.successorator.util;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.lang.Math;

import edu.ucsd.cse110.successorator.lib.domain.DateTracker;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class DateManager {

    private static DateTracker globalDate;


    public static void initializeGlobalDate(DateTracker dateTracker) {
        globalDate = new DateTracker(dateTracker.getDate());
    }

    public static void incrementDate() {
        //Log.d("beforeIncrement", getLocalDateSubject().getValue().toString());
        globalDate.incrementDate();
        //Log.d("afterIncrement", getLocalDateSubject().getValue().toString());

    }

    public static String getFormattedDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE M/dd");
        return dateFormatter.format(globalDate.getDate());
    }

    public static String getTomorrowFormattedDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE M/dd");
        return dateTimeFormatter.format(globalDate.getTomorrow());
    }

    public static MutableSubject<LocalDate> getLocalDateSubject() {
        return globalDate.getDateSubject();
    }

    public static DateTracker getGlobalDate() {
        return globalDate;
    }
    public static String getDayOfWeek(LocalDate date) {
        String dayOfWeek = date.getDayOfWeek().name();
        return dayOfWeek.charAt(0) + dayOfWeek.substring(1).toLowerCase();
    }
    public static String getDateNoYear(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd"); // Use "dd-MM" for day-month format

        return date.format(formatter);
    }

    public static String getDayOfMonth(LocalDate date) {
        String dayOfWeek = date.getDayOfWeek().name();
        String formattedDayOfWeek = dayOfWeek.charAt(0) + dayOfWeek.substring(1).toLowerCase();
        int dayNum = date.getDayOfMonth();
        int weekNum = (int) Math.ceil(dayNum / 7.0);
        String retString = "";

        switch (weekNum) {
            case 1:
                retString += "1st " + formattedDayOfWeek;
                break;
            case 2:
                retString += "2nd " + formattedDayOfWeek;
                break;
            case 3:
                retString += "3rd " + formattedDayOfWeek;
                break;
            case 4:
                retString += "4th " + formattedDayOfWeek;
                break;
            case 5:
                retString += "5th " + formattedDayOfWeek;
                break;
            default:
                retString += "Invalid week number";
                break;
        }

        return retString;
    }

    public static boolean shouldRecur(LocalDate recurDate, LocalDate checkDate, String recurType) {
        if (recurType.equals("weekly")) {
            if (getDayOfWeek(checkDate).equals(getDayOfWeek(recurDate))) {
                return true;
            }
        }
        if (recurType.equals("yearly")) {
            if (getDateNoYear(checkDate).equals(getDateNoYear(recurDate))) {
                return true;
            }
        }
        if (recurType.equals("monthly")) {
            if (getDayOfMonth(recurDate).equals(getDayOfMonth(checkDate))) {
                return true;
            }
        }
        return false;
    }

}
