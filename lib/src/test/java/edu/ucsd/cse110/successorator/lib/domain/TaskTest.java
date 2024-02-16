package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;

public class TaskTest {

    @Test
    public void testGetters() {
        var task = new Task(1, "Task Title", 0, false, LocalDate.now());
        assertEquals(Integer.valueOf(1), task.id());
        assertEquals("Task Title", task.text());
        assertEquals(0, task.sortOrder());
        assertFalse(task.isFinished());
        assertEquals(LocalDate.now(), task.dateCreated());
    }

    @Test
    public void withId() {
        var task = new Task(1, "Task Title", 0, false, LocalDate.now());
        var expected = new Task(40, "Task Title", 0, false, LocalDate.now());
        var actual = task.withId(40);
        assertEquals(expected, actual);
    }

    @Test
    public void withSortOrder() {
        var task = new Task(1, "Task Title", 0, false, LocalDate.now());
        var expected = new Task(1, "Task Title", 4, false, LocalDate.now());
        var actual = task.withSortOrder(4);
        assertEquals(expected, actual);
    }

    @Test
    public void testEquals() {
        var task1 = new Task(1, "text", 0, false, LocalDate.now());
        var task2 = new Task(1, "text", 0, false, LocalDate.now());

        var task3 = new Task(4, "text", 0, false, LocalDate.now());
        var task4 = new Task(1, "wrong text", 0, false, LocalDate.now());
        var task5 = new Task(1, "text", 5, false, LocalDate.now());
        var task6 = new Task(1, "text", 0, true, LocalDate.now());
        var task7 = new Task(1, "text", 0, false, LocalDate.of(2000, 1, 1));

        assertEquals(task1, task2);
        assertNotEquals(task1, task3);
        assertNotEquals(task1, task4);
        assertNotEquals(task1, task5);
        assertNotEquals(task1, task6);
        assertNotEquals(task1, task7);
    }
}