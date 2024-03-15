package edu.ucsd.cse110.successorator.ui.tasklist.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.data.db.LocalDateConverter;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.ui.tasklist.AbstractTaskListFragment;
import edu.ucsd.cse110.successorator.util.DateManager;

public class RecurringAddTaskDialogFragment extends DialogFragment {

    private MainViewModel activityModel;
    private EditText editTextTask;

    private Button startDateButton;

    private int startYear, startMonth, startDay;

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
        view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_recurring_add_task_dialog, null);
        editTextTask = view.findViewById(R.id.edit_text_task);
        startDateButton = view.findViewById(R.id.start_date_button);

        // Initialize Calendar instance to current date
        final LocalDate c = DateManager.getGlobalDate().getDate();
        startYear = c.getYear();
        startMonth = c.getMonthValue();
        startDay = c.getDayOfMonth();

        // Display current date in the button
        startDateButton.setText((startMonth) + "/" + startDay + "/" + startYear);

        // Auto-check the daily button
        RadioButton btn = view.findViewById(R.id.weekly);
        btn.setChecked(true); // Auto-check the weekly button

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
                //setRecurringOptionText();
            }
        });

        // Create the dialog using AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view)
                .setTitle("New Task")
                .setPositiveButton("Save", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick);

        return builder.create();
    }


    public void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dateView, int year, int monthOfYear, int dayOfMonth) {
                        startYear = year;
                        startMonth = monthOfYear + 1;
                        startDay = dayOfMonth;

                        // Display the selected date in the button
                        startDateButton.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        // variable that stores the text input when save is pressed
        @NonNull String taskText = editTextTask.getText().toString();
        // in final product, change date to LocalDate.now()

        String type = getType();
        String context = getTaskContext();
        LocalDate date = LocalDate.of(startYear, startMonth, startDay);
        Task newTask = new Task(null, taskText, -1, false, date, context, type);

        activityModel.append(newTask);

        List<Task> tasks = activityModel.getTaskList().getValue();
        tasks.add(newTask);
        AbstractTaskListFragment.handleRecurrence(tasks, DateManager.getGlobalDate().getDate(), activityModel);

        dismiss();
    }

    private String getType() {
        String type = "daily";

        RadioButton dailyBtn = view.findViewById(R.id.daily);
        RadioButton weeklyBtn = view.findViewById(R.id.weekly);
        RadioButton monthlyBtn = view.findViewById(R.id.monthly);
        RadioButton yearlyBtn = view.findViewById(R.id.yearly);

        if(dailyBtn.isChecked()) {type = "daily";}
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

        if(homeBtn.isChecked()) {context = "home";}
        else if(workBtn.isChecked()) {context = "work";}
        else if (schoolBtn.isChecked()) { context = "school";}
        else if(errandBtn.isChecked()) {context = "errand";}

        return context;
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}
