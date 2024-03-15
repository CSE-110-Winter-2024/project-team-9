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
import java.util.Objects;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;

import edu.ucsd.cse110.successorator.databinding.FragmentTaskListBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.util.DateManager;

public abstract class AbstractTaskListFragment extends Fragment {

    public MainViewModel activityModel;
    private FragmentTaskListBinding view;
    private TaskListAdapter adapter;
    private int totalRecurring;


    public AbstractTaskListFragment() {
        // Required empty public constructor
    }

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

        handleRecurrence(activityModel.getTaskList().getValue(), DateManager.getGlobalDate().getDate(), activityModel);

        activityModel.getTaskList().observe(tasks -> {
            if (tasks == null) return;
            int recurringCount = 0;

            adapter.clear();
            removeRepetition(tasks, activityModel);
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

    public static void removeRepetition(List<Task> tasks, MainViewModel activityModel) {
        if (tasks == null) {
            return;
        }
        for (Task task: tasks) {
            boolean found = false;
            for (Task check: tasks) {
                if (check.equals(task) ||
                        (Objects.equals(task.text(), check.text()) && Objects.equals(task.category(),check.category())
                                        && (task.activeDate().isAfter(check.activeDate()) && !task.activeDate().isAfter(DateManager.getGlobalDate().getDate())
                                        && Objects.equals(task.type(), check.type())
                                ))) {
                    if (found) {
                        activityModel.remove(check.id());
                    } else {
                        found = true;
                    }
                }
            }
        }
    }

    public static void handleRecurrence(List<Task> tasks, LocalDate today, MainViewModel activityModel) {
        if (tasks == null) {
            return;
        }
        LocalDate tomorrow = today.plusDays(1);
        removeRepetition(tasks, activityModel);
        for (Task task: tasks) {
            if (!task.type().equals("single-time") && !task.type().equals("pending")) {
                boolean shouldRecurToday = DateManager.shouldRecur(task.dateCreated(), today, task.type());
                boolean shouldRecurTomorrow = DateManager.shouldRecur(task.dateCreated(), tomorrow, task.type());
                Task toAddTomorrow = new Task(task.id(), task.text(), 1, false, tomorrow, task.category(),"single-time");
                Task toAddToday = new Task(task.id(), task.text(), 1, false, today, task.category(),"single-time");

                if (task.type().equals("daily")) {
                    if (!task.dateCreated().isAfter(today)) {
                        shouldRecurToday = true;
                    }
                    if (!task.dateCreated().isAfter(tomorrow)) {
                        shouldRecurTomorrow = true;
                    }
                }

                boolean addToday = true;
                boolean addTomorrow = true;
                for (Task check: tasks) {
                    if (toAddToday.equals(check)) {
                        addToday = false;
                    }
                    if (toAddTomorrow.equals(check)) {
                        addTomorrow = false;
                    }
                }
                if (addToday && shouldRecurToday) {
                    activityModel.append(toAddToday);
                }
                if (addTomorrow && shouldRecurTomorrow) {
                    activityModel.append(toAddTomorrow);
                }
            }
        }
    }
}
