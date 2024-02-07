package edu.ucsd.cse110.successorator.ui.tasklist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ucsd.cse110.successorator.R;

import edu.ucsd.cse110.successorator.databinding.FragmentTaskListBinding;

public class TaskListFragment extends Fragment {

    private FragmentTaskListBinding view;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = FragmentTaskListBinding.inflate(inflater, container, false);
        setupMvp();
        return view.getRoot();
    }

    private void setupMvp() {
        view.placeholderText.setText(R.string.no_goals);
    }
}