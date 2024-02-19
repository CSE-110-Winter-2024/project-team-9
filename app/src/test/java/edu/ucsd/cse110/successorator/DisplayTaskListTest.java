package edu.ucsd.cse110.successorator;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import edu.ucsd.cse110.successorator.lib.domain.DateTracker;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.DateManager;


public class DisplayTaskListTest {

    @Mock
    private TaskRepository mockTaskRep;
    private MainViewModel mainViewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);


        // Initialize the mock date tracker and date manager
        LocalDate date = LocalDate.of(2004, 2, 5);
        DateTracker mockDateTracker = Mockito.mock(DateTracker.class);
        when(mockDateTracker.getDate()).thenReturn(date);

        DateManager.initializeGlobalDate(mockDateTracker);

        // Define behavior of the mockTaskRep
        List<Task> tasksFromRepository = Arrays.asList(
                new Task(1, "Task 1", 1, false, mockDateTracker.getDate()),
                new Task(2, "Task 2", 2, false, mockDateTracker.getDate()),
                new Task(3, "Task 3", 3, false, mockDateTracker.getDate())
        );

        Subject<List<Task>> subjectFromRepository = new Subject<List<Task>>() {
            @Override
            public List<Task> getValue() {
                return tasksFromRepository;
            }

            @Override
            public void observe(Observer<List<Task>> observer) {
                observer.onChanged(tasksFromRepository);
            }

            @Override
            public void removeObserver(Observer<List<Task>> observer) {
                // Not implemented for this test
            }
        };

        when(mockTaskRep.findAll()).thenReturn(subjectFromRepository);

        // Initialize MainViewModel
        mainViewModel = new MainViewModel(mockTaskRep);
    }

    @Test
    public void testTaskListMatchesRepository() {
        // When
        List<Task> tasksFromViewModel = mainViewModel.getTaskList().getValue();

        // Then
        List<Task> tasksFromRepository = Arrays.asList(
                new Task(1, "Task 1", 1, false, LocalDate.of(2004, 2, 5)),
                new Task(2, "Task 2", 2, false, LocalDate.of(2004, 2, 5)),
                new Task(3, "Task 3", 3, false, LocalDate.of(2004, 2, 5))
        );

        assertEquals(tasksFromRepository.size(), tasksFromViewModel.size());
        for (int i = 0; i < tasksFromRepository.size(); i++) {
            assertEquals(tasksFromRepository.get(i), tasksFromViewModel.get(i));
        }
    }
}
