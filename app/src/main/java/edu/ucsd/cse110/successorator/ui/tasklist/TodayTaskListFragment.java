package edu.ucsd.cse110.successorator.ui.tasklist;

import android.os.Bundle;

import androidx.annotation.Nullable;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.lib.domain.Task;

public class TodayTaskListFragment extends AbstractTaskListFragment{
    private MainViewModel activityModel;

    public static TodayTaskListFragment newInstance() {
        TodayTaskListFragment fragment = new TodayTaskListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public void onDeleteClick(Task task) {
        activityModel.prepend(new Task(task.id(), task.text(), 1, !(task.isFinished()), task.activeDate()));
        activityModel.remove(task.id());
    }
}
