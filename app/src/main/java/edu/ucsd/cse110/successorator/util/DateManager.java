package edu.ucsd.cse110.successorator.util;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import edu.ucsd.cse110.successorator.lib.domain.DateTracker;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class DateManager {

    private static DateTracker globalDate;
    private static DateTracker lastOpenedDate;

    public static void initializeGlobalDate(Context context) {
        lastOpenedDate = globalDate!=null ? new DateTracker(globalDate.getDate()) : null;
        globalDate = new DateTracker(LocalDate.now());
        Log.d("Last, New", lastOpenedDate + " " + globalDate.getDate().toString());
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

    public static MutableSubject<LocalDate> getLocalDateSubject() {
        return globalDate.getDateSubject();
    }

    public static DateTracker getGlobalDate() {
        return globalDate;
    }
}
