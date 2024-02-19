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
import edu.ucsd.cse110.successorator.lib.domain.Task;

public class TaskListFragment extends Fragment {

    private MainViewModel activityModel;
    private FragmentTaskListBinding view;
    private TaskListAdapter adapter;

    private DateManager globalDate;

    public TaskListFragment() {
        // Required empty public constructor
    }

    public static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        this.adapter = new TaskListAdapter(requireContext(), List.of(), task -> {
            activityModel.prepend(new Task(task.id(), task.text(), 1, !(task.isFinished()), task.activeDate()));
            activityModel.remove(task.id());
        });
        activityModel.getTaskList().observe(tasks -> {
            if (tasks == null) return;
            adapter.clear();
            adapter.addAll(new ArrayList<>(tasks)); // remember the mutable copy here!
            adapter.notifyDataSetChanged();
            if (tasks.isEmpty()) {
                view.placeholderText.setText(R.string.no_goals);
            } else {
                view.placeholderText.setText("");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = FragmentTaskListBinding.inflate(inflater, container, false);

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
}