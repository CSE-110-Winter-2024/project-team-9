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
    private static String category;
    public static PendingTaskListFragment newInstance(String filter) {

        // filter will be "home", "work", "school", "errands", or "" for none
        PendingTaskListFragment fragment = new PendingTaskListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        category = filter;
        return fragment;
    }
    @Nullable
    @Override
    public void onDeleteClick(Task task) {
        var dialogFragment = DeleteTaskDialogFragment.newInstance(task);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        dialogFragment.show(fm, "delete task dialog fragment");
    }

    @Nullable
    @Override
    public ArrayList<Task> filterTasks(List<Task> tasks) {
        ArrayList<Task> pendingTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (category.equals("")) {
                if (task.type().equals("pending")) {
                    pendingTasks.add(task);
                }
            }
            else{
                if (task.type().equals("pending") && task.category().equals(category)) {
                    pendingTasks.add(task);
                }
            }
        }
        return pendingTasks;
    }
}
