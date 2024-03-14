package edu.ucsd.cse110.successorator.ui.tasklist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;

import edu.ucsd.cse110.successorator.databinding.FragmentTaskListBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.util.DateManager;

abstract class AbstractTaskListFragment extends Fragment {

    public MainViewModel activityModel;
    private FragmentTaskListBinding view;
    private TaskListAdapter adapter;

    public AbstractTaskListFragment() {
        // Required empty public constructor
    }

//    public static TaskListFragment newInstance(String filter) {
//        filter will be "home", "work", "school", "errands", or "" for none
//        TaskListFragment fragment = new TaskListFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the Model
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

        // Initialize the Adapter (with an empty list for now)
        this.adapter = new TaskListAdapter(requireContext(), List.of(), this::onDeleteClick);
    }

    @Nullable
    public void onDeleteClick(Task task) {
        return;
    }

    @Nullable
    public ArrayList<Task> filterTasks(List<Task> tasks) {
        return new ArrayList<>(tasks);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = FragmentTaskListBinding.inflate(inflater, container, false);

        activityModel.updateTasks();
        activityModel.updateActiveTasks();
        activityModel.deletePrevFinished();

        if (activityModel.getTaskList().getValue() != null) {
            handleRecurrence(activityModel.getTaskList().getValue());
        }
        activityModel.getTaskList().observe(tasks -> {
            if (tasks == null) return;
            adapter.clear();
            ArrayList<Task> taskList = filterTasks(tasks);
            adapter.addAll(taskList); // remember the mutable copy here!
            adapter.notifyDataSetChanged();
            if (tasks.isEmpty()) {
                view.placeholderText.setText(R.string.no_goals);
            } else {
                view.placeholderText.setText("");
            }
        });

        view.moveDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button Clicked");
                DateManager.incrementDate();
                Log.d("onClick", "Date Incremented");
                activityModel.updateTasks();
                activityModel.updateActiveTasks();
                activityModel.deletePrevFinished();
            }
        });




        // Set the adapter on the ListView
        view.cardList.setAdapter(adapter);

        return view.getRoot();
    }

    public void handleRecurrence(List<Task> tasks) {
        LocalDate today = DateManager.getGlobalDate().getDate();
        LocalDate tomorrow = DateManager.getGlobalDate().getTomorrow();
        for (Task task: tasks) {
            if (task.type().equals("daily")) {
                boolean addToday = true;
                boolean addTomorrow = true;
                for (Task checkTask: tasks) {
                    if (checkTask.text().equals(task.text()) && checkTask.type().equals("single-time") && !checkTask.activeDate().isAfter(today)) {
                        addToday = false;
                    }
                    if (checkTask.text().equals(task.text()) && checkTask.type().equals("single-time") && checkTask.activeDate().equals(tomorrow)) {
                        addTomorrow = false;
                    }
                }
                if (addToday) {
                    activityModel.append(new Task(task.id(), task.text(), 1, false, today, task.category(),"single-time"));
                }
                if (addTomorrow) {
                    activityModel.append(new Task(task.id(), task.text(), 1, false, tomorrow, task.category(),"single-time"));
                }
            }
            if (!task.type().equals("single-time") && !task.type().equals("pending")) {
                boolean shouldRecurToday = false;
                if (task.type().equals("daily")) {
                    shouldRecurToday = true;
                } else {
                    shouldRecurToday = DateManager.shouldRecur(task.dateCreated(), DateManager.getGlobalDate().getDate(), task.type());
                }

            }
        }
    }
}
