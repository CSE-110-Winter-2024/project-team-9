package edu.ucsd.cse110.successorator.ui.addtask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentAddTaskBinding;
import edu.ucsd.cse110.successorator.ui.addtask.AddTaskFragment;

public class AddTaskFragment extends Fragment {

    private FragmentAddTaskBinding view;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragment newInstance() {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = FragmentAddTaskBinding.inflate(inflater, container, false);
        setupMvp();
        return view.getRoot();
    }

    private void setupMvp() {
        view.newTaskTitle.setHint(R.string.input_text);
    }
}
