package edu.ucsd.cse110.successorator.ui.tasklist;

import android.os.Bundle;

import androidx.annotation.Nullable;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.util.DateManager;

public class TodayTaskListFragment extends AbstractTaskListFragment{

    public static TodayTaskListFragment newInstance() {
        TodayTaskListFragment fragment = new TodayTaskListFragment();
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
            if (task.type().equals("single-time") && !task.activeDate().isAfter(DateManager.getGlobalDate().getDate())) {
                todayTasks.add(task);
            }
        }
        return todayTasks;
    }
}
