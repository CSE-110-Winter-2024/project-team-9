package edu.ucsd.cse110.successorator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;

import edu.ucsd.cse110.successorator.util.DateManager;

@RunWith(JUnit4.class)
public class DateSuffixTest {

    @Test
    public void testGetDayOfWeek() {
        LocalDate date = LocalDate.of(2024,1,1);
        assert(DateManager.getDayOfWeek(date).equals("Monday"));

        date = LocalDate.of(2024,1,2);
        assert(DateManager.getDayOfWeek(date).equals("Tuesday"));

        date = LocalDate.of(2024,1,3);
        assert(DateManager.getDayOfWeek(date).equals("Wednesday"));

        date = LocalDate.of(2024,1,4);
        assert(DateManager.getDayOfWeek(date).equals("Thursday"));

        date = LocalDate.of(2024,1,5);
        assert(DateManager.getDayOfWeek(date).equals("Friday"));

        date = LocalDate.of(2024,1,6);
        assert(DateManager.getDayOfWeek(date).equals("Saturday"));

        date = LocalDate.of(2024,1,7);
        assert(DateManager.getDayOfWeek(date).equals("Sunday"));

        date = LocalDate.of(2024,1,8);
        assert(DateManager.getDayOfWeek(date).equals("Monday"));
    }

    @Test
    public void testGetDateNoYear() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        assert(DateManager.getDateNoYear(date).equals("01/01"));

        date = LocalDate.of(2024, 2, 29);
        assert(DateManager.getDateNoYear(date).equals("02/29"));

        date = LocalDate.of(2024, 12, 25);
        assert(DateManager.getDateNoYear(date).equals("12/25"));

        date = LocalDate.of(2024, 7, 31);
        assert(DateManager.getDateNoYear(date).equals("07/31"));

        date = LocalDate.of(2024, 6, 30);
        assert(DateManager.getDateNoYear(date).equals("06/30"));
    }

    @Test
    public void testGetDayOfMonth() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        assert(DateManager.getDayOfMonth(date).equals("1st Monday"));

        date = LocalDate.of(2024, 1, 7);
        assert(DateManager.getDayOfMonth(date).equals("1st Sunday"));

        date = LocalDate.of(2024, 1, 8);
        assert(DateManager.getDayOfMonth(date).equals("2nd Monday"));

        date = LocalDate.of(2024, 1, 14);
        assert(DateManager.getDayOfMonth(date).equals("2nd Sunday"));

        date = LocalDate.of(2024, 1, 15);
        assert(DateManager.getDayOfMonth(date).equals("3rd Monday"));

        date = LocalDate.of(2024, 1, 21);
        assert(DateManager.getDayOfMonth(date).equals("3rd Sunday"));

        date = LocalDate.of(2024, 1, 22);
        assert(DateManager.getDayOfMonth(date).equals("4th Monday"));

        date = LocalDate.of(2024, 1, 28);
        assert(DateManager.getDayOfMonth(date).equals("4th Sunday"));

        date = LocalDate.of(2024, 1, 29);
        assert(DateManager.getDayOfMonth(date).equals("5th Monday"));
    }
}
