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
import java.time.format.TextStyle;
import java.util.Locale;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.util.DateManager;

public class TomorrowAddTaskDialogFragment extends DialogFragment {

    private MainViewModel activityModel;
    private EditText editTextTask;

    private DateManager dateManager;

    private View view;

    public TomorrowAddTaskDialogFragment() {
        // Required empty constructor
    }

    public static TomorrowAddTaskDialogFragment newInstance() {
        return new TomorrowAddTaskDialogFragment();
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
        RadioButton btn = view.findViewById(R.id.singleTime);
        btn.setChecked(true);

        LocalDate date = dateManager.getGlobalDate().getDate();
        date = date.plusDays(1);
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US);
        int dayOfMonth = date.getDayOfMonth();
        int month = date.getMonthValue();

        int occurrences = 0;
        while (date.getMonthValue() == month) {
            if (date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US).equals(dayOfWeek)) {
                occurrences++;
            }
            date = date.plusDays(1);
        }

        RadioButton weekly = view.findViewById(R.id.weekly);
        weekly.setText(String.format("Weekly on %s", dayOfWeek));

        RadioButton monthly = view.findViewById(R.id.monthly);
        monthly.setText(String.format("Monthly on %s %s", formatNumberWithSuffix(occurrences), dayOfWeek));

        RadioButton yearly = view.findViewById(R.id.yearly);
        yearly.setText(String.format("Yearly on %d/%d", month, dayOfMonth));

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

        String type = getType();
        String context = getTaskContext();
        // in final product, change date to LocalDate.now()
        LocalDate date = dateManager.getGlobalDate().getDate();
        Task newTask = new Task(null, taskText, -1, false, date.plusDays(1), context, type);
        activityModel.append(newTask);

        dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        Log.d("onNegativeButtonClick", "cancel");
        dialog.cancel();
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

    private String getTaskContext() {
        String context = "home";
        RadioButton homeBtn = view.findViewById(R.id.contextHome);
        RadioButton workBtn = view.findViewById(R.id.contextWork);
        RadioButton schoolBtn = view.findViewById(R.id.contextSchool);
        RadioButton errandBtn = view.findViewById(R.id.contextErrand);

        if(homeBtn.isChecked()) {context = "single-time";}
        else if(workBtn.isChecked()) {context = "daily";}
        else if (schoolBtn.isChecked()) { context = "weekly";}
        else if(errandBtn.isChecked()) {context = "monthly";}

        return context;
    }

    public static String formatNumberWithSuffix(int number) {
        if (number >= 11 && number <= 13) {
            return number + "th"; // Special case for 11th, 12th, and 13th
        } else {
            switch (number % 10) {
                case 1: return number + "st";
                case 2: return number + "nd";
                case 3: return number + "rd";
                default: return number + "th";
            }
        }
    }
}
