package edu.ucsd.cse110.successorator.ui.tasklist;

import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.util.DateManager;

public class TomorrowTaskListFragment extends AbstractTaskListFragment{
    public static TomorrowTaskListFragment newInstance() {
        TomorrowTaskListFragment fragment = new TomorrowTaskListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public void onDeleteClick(Task task) {
        activityModel.remove(task.id());
        activityModel.prepend(new Task(task.id(), task.text(), 1, !(task.isFinished()), task.activeDate(), task.category(),task.type()));
    }

    @Nullable
    @Override
    public ArrayList<Task> filterTasks(List<Task> tasks) {
        ArrayList<Task> todayTasks = new ArrayList<>();
        for (Task task: tasks) {
            System.out.println(task.text());
            if (task.type().equals("single-time") && task.activeDate().isEqual(DateManager.getGlobalDate().getTomorrow())) {
                todayTasks.add(task);
                System.out.println("here");
            }
        }
        return todayTasks;
    }
}