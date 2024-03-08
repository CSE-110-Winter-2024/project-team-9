package edu.ucsd.cse110.successorator.ui.tasklist.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.util.DateManager;

public class RecurringAddTaskDialogFragment extends DialogFragment {

    private MainViewModel activityModel;
    private EditText editTextTask;

    private DateManager dateManager;

    private View view;

    public RecurringAddTaskDialogFragment() {
        // Required empty constructor
    }

    public static RecurringAddTaskDialogFragment newInstance() {
        return new RecurringAddTaskDialogFragment();
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
        view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_task_dialog, null);
        editTextTask = view.findViewById(R.id.edit_text_task);

        // Create the dialog using AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view)
                .setTitle("New Task")
                .setMessage("Please enter the title of your task")
                .setPositiveButton("Save", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick);

        return builder.create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        // variable that stores the text input when check icon is pressed
        @NonNull String taskText = editTextTask.getText().toString();
        Log.d("onPositiveButtonClick", "Button Pressed");
        // in final product, change date to LocalDate.now()

        String type = getType();
        Log.d("type:", type);
        LocalDate date = dateManager.getGlobalDate().getDate();
        Task newTask = new Task(null, taskText, -1, false, date, "", type);
        activityModel.append(newTask);

        dismiss();
    }

    private String getType() {
        String type = "single-time";
        RadioButton singleBtn = view.findViewById(R.id.singleTime);
        RadioButton dailyBtn = view.findViewById(R.id.daily);
        RadioButton weeklyBtn = view.findViewById(R.id.weekly);
        RadioButton monthlyBtn = view.findViewById(R.id.monthly);
        RadioButton yearlyBtn = view.findViewById(R.id.yearly);
        if(singleBtn.isChecked()) {type = "single-time";}
        else if(dailyBtn.isChecked()) {type = "daily";}
        else if (weeklyBtn.isChecked()) { type = "weekly";}
        else if(monthlyBtn.isChecked()) {type = "monthly";}
        else if (yearlyBtn.isChecked()) { type = "yearly";}

        return type;
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        Log.d("onNegativeButtonClick", "cancel");
        dialog.cancel();
    }
}
