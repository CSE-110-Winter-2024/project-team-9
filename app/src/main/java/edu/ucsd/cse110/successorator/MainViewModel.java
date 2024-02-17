package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.DateTracker;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;


public class MainViewModel extends ViewModel {

    private final TaskRepository taskRepository;
    private final DateTracker dateTracker = new DateTracker();


    private final MutableSubject<List<Task>> taskList;


    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getTaskRepository());
                    });

    public MainViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

        this.taskList = new SimpleSubject<>();

        taskRepository.findAll().observe(tasks -> {
            if (tasks == null) return;

            var newTasks = tasks.stream().collect(Collectors.toList());

            taskList.setValue(newTasks);
        });


    }

    public Subject<List<Task>> getTaskList() {
        return taskList;
    }

    public void append(Task task) {
        taskRepository.append(task);
    }

    public void remove(int id) {
        taskRepository.remove(id);
    }

}

