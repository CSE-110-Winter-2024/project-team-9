package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.DateManager;


public class MainViewModel extends ViewModel {

    private final TaskRepository taskRepository;

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

        resetFutureTasks();

        updateTasks();
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
        LocalDateTime currentTime = LocalDateTime.now();
        boolean isBetweenMidnightAnd2AM = currentTime.getHour() >= 0 && currentTime.getHour() < 2;

        taskRepository.findAll().observe(tasks -> {
            if (tasks == null) return;

            var newTasks = tasks.stream()
                    .filter(task -> {
                        // If it's between midnight and 2:00 AM, include tasks from both the current day and the previous day
                        if (isBetweenMidnightAnd2AM) {
                            return task.activeDate().equals(DateManager.getGlobalDate().getDate().minusDays(1))
                                    || task.activeDate().equals(DateManager.getGlobalDate().getDate());
                        } else {
                            // Otherwise, include tasks from the current day
                            return task.activeDate().equals(DateManager.getGlobalDate().getDate());
                        }
                    })
                    .collect(Collectors.toList());

            //Log.d("dateManager.getGlobalDate().getDate()", DateManager.getGlobalDate().getDate().toString());
            taskList.setValue(newTasks);
        });
    }




    public void updateActiveTasks() {
        taskRepository.updateActiveTasks();
    }

    public void resetFutureTasks() {
        taskRepository.resetFutureTasks();
    }

    public void deletePrevFinished() {
        taskRepository.deletePrevFinished();
    }


}

