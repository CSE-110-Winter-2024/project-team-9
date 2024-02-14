package edu.ucsd.cse110.successorator.ui.tasklist.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;

public class AddTaskDialogFragment extends DialogFragment {

    private MainViewModel activityModel;
    private EditText editTextTask;

    public AddTaskDialogFragment() {
        // Required empty constructor
    }

    public static AddTaskDialogFragment newInstance() {
        return new AddTaskDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the custom layout for the dialog
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_task_dialog, null);
        editTextTask = view.findViewById(R.id.edit_text_task);

        // Set the action listener for the EditText
        editTextTask.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Handle "done" action
                onPositiveButtonClick();
                return true;
            }
            return false;
        });

        // Create the dialog using AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view)
                .setNegativeButton("Cancel", this::onNegativeButtonClick);

        return builder.create();
    }

    private void onPositiveButtonClick() {
        // variable that stores the text inputted when check icon is pressed
        String taskText = editTextTask.getText().toString();
        dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}
