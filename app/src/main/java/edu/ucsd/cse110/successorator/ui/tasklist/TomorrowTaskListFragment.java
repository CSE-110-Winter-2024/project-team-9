package edu.ucsd.cse110.successorator.ui.tasklist;

import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.util.DateManager;

public class TomorrowTaskListFragment extends AbstractTaskListFragment{
    private static String category = "";

    public static TomorrowTaskListFragment newInstance(String filter) {
        // filter will be "home", "work", "school", "errands", or "" for none
        TomorrowTaskListFragment fragment = new TomorrowTaskListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        category = filter;
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
        ArrayList<Task> tomorrowTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (category.equals("")){
                if (task.type().equals("single-time") && task.activeDate().isEqual(DateManager.getGlobalDate().getTomorrow())) {
                    tomorrowTasks.add(task);
                }
            }
            else{
                if (task.type().equals("single-time") && task.activeDate().isEqual(DateManager.getGlobalDate().getTomorrow())
                        && task.category().equals(category)) {
                    tomorrowTasks.add(task);
                }
            }
        }
        return tomorrowTasks;
    }
}
