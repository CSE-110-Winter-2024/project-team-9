package edu.ucsd.cse110.successorator.ui.tasklist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.ui.tasklist.dialog.DeleteTaskDialogFragment;
import edu.ucsd.cse110.successorator.ui.tasklist.dialog.SwitchViewDialogFragment;

public class PendingTaskListFragment extends AbstractTaskListFragment {
    public static TomorrowTaskListFragment newInstance() {
        TomorrowTaskListFragment fragment = new TomorrowTaskListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public void onDeleteClick(Task task) {
        var dialogFragment = DeleteTaskDialogFragment.newInstance();
//        FragmentManager fm = getSupportFragmentManager();
//        dialogFragment.show(fm, "delete task dialog fragment");
    }

    @Nullable
    @Override
    public ArrayList<Task> filterTasks(List<Task> tasks) {
        ArrayList<Task> pendingTasks = new ArrayList<>();
        for (Task task: tasks) {
            System.out.println(task.text());
            if (task.type().equals("pending")) {
                pendingTasks.add(task);
            }
        }
        return pendingTasks;
    }
}
