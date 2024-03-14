package edu.ucsd.cse110.successorator.ui.tasklist.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.ui.tasklist.PendingTaskListFragment;

public class ChangeFilterDialogFragment extends DialogFragment {

    private MainViewModel activityModel;

    private View view;

    public interface OnInputListener {
        void sendFilterInput(String input);
    }
    private ChangeFilterDialogFragment.OnInputListener myListener;

    public ChangeFilterDialogFragment() {
        // Required empty public constructor
    }

    public static ChangeFilterDialogFragment newInstance() {

        return new ChangeFilterDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);


        try {
            myListener = (ChangeFilterDialogFragment.OnInputListener) getActivity();

        } catch (ClassCastException error) {
            Log.e("Error", "onCreate: ClassCastException: " + error.getMessage());
        }
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_change_filter_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view)
                .setTitle("Select Filter")
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .setPositiveButton("Confirm", this::onPositiveButtonClick);

        return builder.create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        dialog.dismiss();

        RadioButton homeBtn = view.findViewById(R.id.homeButton);
        RadioButton workBtn = view.findViewById(R.id.workButton);
        RadioButton schoolBtn = view.findViewById(R.id.schoolButton);
        RadioButton errandsBtn = view.findViewById(R.id.errandsButton);
        RadioButton otherBtn = view.findViewById(R.id.otherButton);

        String input = "";
        if (homeBtn.isChecked()) {
            input = "home";
        } else if (workBtn.isChecked()) {
            input = "work";
        } else if (schoolBtn.isChecked()) {
            input = "school";
        } else if (errandsBtn.isChecked()) {
            input = "errand";
        } else if (otherBtn.isChecked()) {
            input = "";
        } else {
            return;
        }
        myListener.sendFilterInput(input);
        dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dismiss();
    }
}