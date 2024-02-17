package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.time.LocalDate;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class DateTracker {
    private LocalDate date;
    private MutableSubject<LocalDate> dateSubject;

    public DateTracker(@NonNull LocalDate date) {
        this.date = date;
        this.dateSubject = new SimpleSubject<LocalDate>();
        dateSubject.setValue(date);
    }
    public DateTracker() {
        this.date = LocalDate.now();
    }

    public void setDate(LocalDate newDate) {
        this.date = newDate;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public MutableSubject<LocalDate> getDateSubject() {;
        dateSubject.setValue(getDate());
        return dateSubject;
    }

    public void incrementDate() {
        LocalDate newDate = date.plusDays(1);
        getDateSubject().setValue(newDate);
        this.date = newDate;

    }
}
