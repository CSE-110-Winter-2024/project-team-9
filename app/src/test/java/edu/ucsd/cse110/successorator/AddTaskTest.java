package edu.ucsd.cse110.successorator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class AddTaskTest {

    @Mock
    private TaskRepository mockTaskRep;
    private MainViewModel mainViewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        // Initialize the mock repository
        LocalDate date = LocalDate.of(2004, 2, 5);
        List<Task> tasksFromRepository = Arrays.asList(
                new Task(1, "Task 1", 1, false, date),
                new Task(2, "Task 2", 2, false, date)
        );

        // Define the behavior of the mock repository
        Subject<List<Task>> subjectFromRepository = new SimpleSubject<>();
        ((SimpleSubject<List<Task>>) subjectFromRepository).setValue(tasksFromRepository);

        when(mockTaskRep.findAll()).thenReturn(subjectFromRepository);

        // Initialize MainViewModel with the mock repository
        mainViewModel = new MainViewModel(mockTaskRep);
    }

    @Test
    public void testAppendTask() {
        // Given
        LocalDate date = LocalDate.of(2004, 2, 5);
        Task taskToAdd = new Task(3, "Task 3", 3, false, date);

        // When
        mainViewModel.append(taskToAdd);

        // Then
        // Verify that the task is appended in the repository
        verify(mockTaskRep).append(taskToAdd);
    }
}

