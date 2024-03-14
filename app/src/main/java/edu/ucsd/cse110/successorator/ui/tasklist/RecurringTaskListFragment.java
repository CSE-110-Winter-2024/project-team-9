package edu.ucsd.cse110.successorator.ui.tasklist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.ui.tasklist.dialog.DeleteRecurringTaskDialogFragment;


public class RecurringTaskListFragment extends AbstractTaskListFragment {
    private static String category = "";
    public static RecurringTaskListFragment newInstance(String filter) {
        RecurringTaskListFragment fragment = new RecurringTaskListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        category = filter;
        return fragment;
    }
    @Nullable
    @Override
    public void onDeleteClick(Task task) {
        //Add call to dialog fragment here
        var dialogFragment = DeleteRecurringTaskDialogFragment.newInstance(task);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        dialogFragment.show(fm, "Delete Recurring Task Dialog Fragment");
        //System.out.println("Recurring long hold");
    }

    @Nullable
    @Override
    public ArrayList<Task> filterTasks(List<Task> tasks) {
        ArrayList<Task> recurringTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (category.equals("")) {
                if (task.type().equals("daily") || task.type().equals("weekly") || task.type().equals("monthly") || task.type().equals("yearly")) {
                    recurringTasks.add(task);
                }
                System.out.println(task.dateCreated());
            }
            else{
                if ((task.type().equals("daily") || task.type().equals("weekly") || task.type().equals("monthly")
                        || task.type().equals("yearly")) && task.category().equals(category) ) {
                    recurringTasks.add(task);
                }
                System.out.println(task.dateCreated());
            }
        }
        return recurringTasks;
    }
}
