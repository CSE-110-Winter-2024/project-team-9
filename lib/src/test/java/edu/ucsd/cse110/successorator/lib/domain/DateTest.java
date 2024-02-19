package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import net.bytebuddy.asm.Advice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Date;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Observer;

@RunWith(MockitoJUnitRunner.class)
public class DateTest {
    @Mock
    private Observer<LocalDate> observer;

    @Test
    public void testDateTrackerMethods() {

        //test getters
        var DateTracker = new DateTracker(LocalDate.now());
        assertEquals(LocalDate.now(), DateTracker.getDate());

        //test incrementDate
        DateTracker.incrementDate();
        assertEquals(LocalDate.now().plusDays(1), DateTracker.getDate());

        //test setDate
        LocalDate newDate = LocalDate.now();
        DateTracker.setDate(newDate);
        assertEquals(newDate, DateTracker.getDate());
    }

    @Test
    public void testObserver() {
        // Create a mock observer
        Observer<LocalDate> observer = Mockito.mock(Observer.class);

        // Create a DateTracker instance
        DateTracker dateTracker = new DateTracker(LocalDate.now());

        // Register the mock observer with the DateTracker
        dateTracker.getDateSubject().observe(observer);

        // Set a new date on the DateTracker
        LocalDate newDate = LocalDate.now();
        dateTracker.setDate(newDate);
        dateTracker.incrementDate();

        // Verify that the observer's onChanged method was called with the new date
        verify(observer).onChanged(eq(newDate));

    }



    @Test
    public void testObserverRandomDate() {
        // Create a mock observer
        Observer<LocalDate> observer = Mockito.mock(Observer.class);

        LocalDate date = LocalDate.of(2004, 06, 28);
        // Create a DateTracker instance
        DateTracker dateTracker = new DateTracker(date);

        // Register the mock observer with the DateTracker
        dateTracker.getDateSubject().observe(observer);

        // Set a new date on the DateTracker
        LocalDate newDate = LocalDate.of(2004, 06, 28);
        dateTracker.setDate(newDate);
        dateTracker.incrementDate();

        // Verify that the observer's onChanged method was called with the new date
        verify(observer).onChanged(eq(newDate));

    }

}
