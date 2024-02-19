package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.DateTracker;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.DateManager;


public class MainViewModel extends ViewModel {

    private final TaskRepository taskRepository;

    private final DateManager dateManager = new DateManager();

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


        updateTasks();
        resetFutureTasks();

    }

    public Subject<List<Task>> getTaskList() {
        return taskList;
    }

    public void prepend(Task task) {
        taskRepository.prepend(task);
    }

    public void append(Task task) {
        taskRepository.append(task);
    }

    public void remove(int id) {
        taskRepository.remove(id);
    }

    public void updateTasks() {
        taskRepository.findAll().observe(tasks -> {
            if (tasks == null) return;

            var newTasks = tasks.stream()
                    .filter(task -> task.activeDate().equals(dateManager.getGlobalDate().getDate()))
                    .collect(Collectors.toList());

            Log.d("dateManager.getGlobalDate().getDate()", dateManager.getGlobalDate().getDate().toString());
            taskList.setValue(newTasks);
        });
    }

    public void updateActiveTasks() {
        taskRepository.updateActiveTasks();
    }

    public void resetFutureTasks() {
        taskRepository.resetFutureTasks();
    }

    public void deletePrevUnfinished() {
        taskRepository.deletePrevUnfinished();
    }


}

