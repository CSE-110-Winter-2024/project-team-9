package edu.ucsd.cse110.successorator.data.db;

import android.util.Log;

import androidx.lifecycle.Transformations;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.util.DateManager;
import edu.ucsd.cse110.successorator.util.LiveDataSubjectAdapter;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class RoomTaskRepository implements TaskRepository {
    private final TaskDao taskDao;

    public RoomTaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public Subject<Task> find(int id) {
        var entityLiveData = taskDao.findAsLiveData(id);
        var taskLiveData = Transformations.map(entityLiveData, TaskEntity::toTask);
        return new LiveDataSubjectAdapter<>(taskLiveData);
    }

    @Override
    public Subject<List<Task>> findAll() {
        var entityLiveData = taskDao.findAllAsLiveData();
        var taskLiveData = Transformations.map(entityLiveData, entities -> {
            return entities.stream()
                    .map(TaskEntity::toTask)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(taskLiveData);
    }

    @Override
    public void save(Task task) {
        taskDao.insert(TaskEntity.fromTask(task));
    }

    @Override
    public void save(List<Task> tasks) {
        var entities = tasks.stream()
                .map(TaskEntity::fromTask)
                .collect(Collectors.toList());
        taskDao.insert(entities);
    }

    @Override
    public void append(Task task) {
        taskDao.append(TaskEntity.fromTask(task));
    }

    @Override
    public void prepend(Task task) {
        taskDao.prepend(TaskEntity.fromTask(task));
    }

    @Override
    public void remove(int id) {
        taskDao.delete(id);
    }

    public void incrementDate(Task task) {
        taskDao.updateTaskDates(task.activeDate());
    }

    @Override
    public void updateActiveTasks() {
        List<TaskEntity> activeTasks = taskDao.getActiveTasks();
        for (int i = 0; i < activeTasks.size(); i++){
            TaskEntity currTask = activeTasks.get(i);
            if (!currTask.activeDate.equals(DateManager.getGlobalDate().getDate())) {
                taskDao.setActiveDate(currTask.id, DateManager.getGlobalDate().getDate());
            }
            Log.d("Active Task: ", activeTasks.get(i).text + " " + activeTasks.get(i).activeDate.toString() + " " + DateManager.getGlobalDate());
        }
    }

    public void resetFutureTasks(){
        List<TaskEntity> allTasks = taskDao.findAll();
        LocalDate dateManagerGlobalDate = DateManager.getGlobalDate().getDate();
        for (int i = 0; i < allTasks.size(); i++){
            TaskEntity currTask = allTasks.get(i);

            if(currTask.activeDate.isAfter(dateManagerGlobalDate)){
                taskDao.setActiveDate(currTask.id, dateManagerGlobalDate);
                taskDao.setIsFinished(currTask.id, false);
            }
        }
    }

    public void deletePrevFinished() {
        List<TaskEntity> allTasks = taskDao.findAll();
        LocalDate dateManagerGlobalDate = DateManager.getGlobalDate().getDate();

        for (int i = 0; i< allTasks.size(); i++){
            TaskEntity currTask = allTasks.get(i);

            if(currTask.isFinished && currTask.activeDate.isBefore(dateManagerGlobalDate)){
                taskDao.delete(currTask.id);
            }
        }
    }

}
