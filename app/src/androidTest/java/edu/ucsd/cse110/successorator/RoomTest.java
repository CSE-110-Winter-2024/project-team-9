package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import edu.ucsd.cse110.successorator.data.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.data.db.TaskEntity;
import edu.ucsd.cse110.successorator.lib.domain.Task;

@RunWith(AndroidJUnit4.class)
public class RoomTest {


    private SuccessoratorDatabase successoratorDatabase;

    @Before
    public void setup() {
        successoratorDatabase = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        SuccessoratorDatabase.class)
                .allowMainThreadQueries()
                .build();

    }

    @After
    public void closeDatabase() {
        successoratorDatabase.close();
    }

    @Test
    public void testUpdate() {
        LocalDate date1 = LocalDate.of(2012, 12, 25);
        TaskEntity task1 = TaskEntity.fromTask(new Task(1, "Task 1", 1, false, date1));

        successoratorDatabase.taskDao().prepend(task1);

        LocalDate date2 = LocalDate.of(2012, 12, 25);
        TaskEntity task2 = TaskEntity.fromTask(new Task(2, "Task 2", 2, false, date2));

        successoratorDatabase.taskDao().append(task2);

        task1 = TaskEntity.fromTask(new Task(3, "Task 1", 1, true, date1));

        successoratorDatabase.taskDao().append(task1);
        successoratorDatabase.taskDao().delete(1);

        Task retrievedTask1 = successoratorDatabase.taskDao().find(3).toTask();
        Task expectedTask1 = new Task(3, "Task 1", 1, true, date1);

        assertEquals(retrievedTask1, expectedTask1);

        task1 = TaskEntity.fromTask(new Task(4, "Task 1", 1, false, date1));

        successoratorDatabase.taskDao().prepend(task1);
        successoratorDatabase.taskDao().delete(3);

        retrievedTask1 = successoratorDatabase.taskDao().find(4).toTask();
        expectedTask1 = new Task(4, "Task 1", 0, false, date1);

        assertEquals(expectedTask1, retrievedTask1);

    }
}