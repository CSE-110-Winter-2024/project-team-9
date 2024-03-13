package edu.ucsd.cse110.successorator;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import edu.ucsd.cse110.successorator.data.db.TaskDao;
import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.databinding.FragmentChangeFilterDialogBinding;
import edu.ucsd.cse110.successorator.lib.domain.DateTracker;
import edu.ucsd.cse110.successorator.ui.tasklist.dialog.ChangeFilterDialogFragment;
import edu.ucsd.cse110.successorator.ui.tasklist.RecurringTaskListFragment;
import edu.ucsd.cse110.successorator.ui.tasklist.PendingTaskListFragment;
import edu.ucsd.cse110.successorator.ui.tasklist.TodayTaskListFragment;
import edu.ucsd.cse110.successorator.ui.tasklist.TomorrowTaskListFragment;
import edu.ucsd.cse110.successorator.ui.tasklist.dialog.PendingAddTaskDialogFragment;
import edu.ucsd.cse110.successorator.ui.tasklist.dialog.TodayAddTaskDialogFragment;
import edu.ucsd.cse110.successorator.ui.tasklist.dialog.RecurringAddTaskDialogFragment;
import edu.ucsd.cse110.successorator.ui.tasklist.dialog.SwitchViewDialogFragment;
import edu.ucsd.cse110.successorator.ui.tasklist.dialog.TomorrowAddTaskDialogFragment;
import edu.ucsd.cse110.successorator.util.DateManager;

public class MainActivity extends AppCompatActivity
        implements SwitchViewDialogFragment.OnInputListener, ChangeFilterDialogFragment.OnInputListener {

    private ActivityMainBinding view;
    private String currentViewName = "today";
    private TaskDao taskDao;
    public static LocalDateTime lastOpened;
    private String contextFilter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        onCreateHelper();

        this.view = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        sendInput(currentViewName);
    }

    private void onCreateHelper() {
        SharedPreferences sharedPreferences = getSharedPreferences("task", MODE_PRIVATE);

        DateTracker dateTracker = new DateTracker(LocalDate.now());
        DateManager.initializeGlobalDate(dateTracker);


        DateManager.getLocalDateSubject().observe(localDate -> {
            if (localDate == null) return;
            sendInput(currentViewName);
        });

        // Retrieve and save last opened datetime
        LocalDateTime lastOpenedDateTime = getLastOpenedDateTime(sharedPreferences);
        if (lastOpenedDateTime == null) {
            lastOpenedDateTime = LocalDateTime.now();
            saveLastOpenedDateTime(sharedPreferences, lastOpenedDateTime);
        }

        lastOpened = lastOpenedDateTime;

        this.view = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        contextFilter = "";
        sendInput("today");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.header_bar, menu);

        Drawable menuIcon = getDrawable(R.drawable.ic_menu);
        menuIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        ShapeDrawable background = new ShapeDrawable(new RectShape());
        background.getPaint().setColor(0x00000000);
        background.setBounds(0, 0, 100, 100);

        LayerDrawable homeAsUpDrawable = new LayerDrawable(new Drawable[] {background, menuIcon});

        getSupportActionBar().setHomeAsUpIndicator(homeAsUpDrawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return true;
    }

    //Defines functionality for when menu item "item" is pressed
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();

        if (itemId == android.R.id.home) {

            var dialogFragment = ChangeFilterDialogFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            dialogFragment.show(fm, "ChangeFilterDialogFragment");
        }

        if (itemId == R.id.header_bar_dropdown) {
            var dialogFragment = SwitchViewDialogFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            dialogFragment.show(fm, "SwitchViewDialogFragment");
        }

        //if item is the add task button, run initiateAddTask()
        if (itemId == R.id.header_bar_add_task) {
            // shows the AddTaskDialogBox fragment when + button is clicked
            switch(currentViewName) {
                case "today":
                    var dialogFragment = TodayAddTaskDialogFragment.newInstance();
                    FragmentManager fm = getSupportFragmentManager();
                    dialogFragment.show(fm, "AddTaskDialogFragment");
                    break;
                case "tomorrow":
                    var tomorrowDialogFragment = TomorrowAddTaskDialogFragment.newInstance();
                    FragmentManager tomorrowFm = getSupportFragmentManager();
                    tomorrowDialogFragment.show(tomorrowFm, "AddTaskDialogFragment");
                    break;
                case "pending":
                    var pendingDialogFragment = PendingAddTaskDialogFragment.newInstance();
                    FragmentManager pendingFm = getSupportFragmentManager();
                    pendingDialogFragment.show(pendingFm, "AddTaskDialogFragment");
                    break;
                case "recurring":
                    var recurringDialogFragment = RecurringAddTaskDialogFragment.newInstance();
                    FragmentManager recurringFm = getSupportFragmentManager();
                    recurringDialogFragment.show(recurringFm, "AddTaskDialogFragment");
                    break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    // Save LocalDateTime to SharedPreferences
    private void saveLastOpenedDateTime(SharedPreferences sharedPreferences, LocalDateTime dateTime) {
        String dateTimeString = dateTime.toString();
        sharedPreferences.edit().putString("last_opened_datetime", dateTimeString).apply();
    }

    // Retrieve LocalDateTime from SharedPreferences
    private LocalDateTime getLastOpenedDateTime(SharedPreferences sharedPreferences) {
        String dateTimeString = sharedPreferences.getString("last_opened_datetime", null);
        if (dateTimeString != null) {
            return LocalDateTime.parse(dateTimeString);
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart");
        Log.d("Last opened datetime: ", lastOpened.toString());
        SharedPreferences sharedPreferences = getSharedPreferences("task", MODE_PRIVATE);
        // Save current time to SharedPreferences
        LocalDateTime currentTime = LocalDateTime.now();
        saveLastOpenedDateTime(sharedPreferences, currentTime);

        Log.d("App started at: ", currentTime.toString());

        MainViewModel mainActivityViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Calculate the rollover deadline for the current day
        LocalDateTime rolloverDeadline = lastOpened.toLocalDate().plusDays(1).atTime(2, 0);
        if (lastOpened.toLocalTime().isBefore(LocalTime.of(2, 0))) {
            // If lastOpened is between 12 am and 2 am, rollover should occur at 2 am of the same day
            rolloverDeadline = rolloverDeadline.minusDays(1);
        }

        if (currentTime.isAfter(rolloverDeadline)){
            Log.d("MainActivity", "Rollover initiated");
            mainActivityViewModel.updateTasks();
            mainActivityViewModel.updateActiveTasks();
            mainActivityViewModel.deletePrevFinished();
        }
    }

    public void updateFilter() {

            Drawable menuIcon = getDrawable(R.drawable.ic_menu);
            ShapeDrawable background = new ShapeDrawable(new OvalShape());
            background.setBounds(0, 0, 100, 100);

            menuIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

            switch (contextFilter) {
                case "home":
                    background.getPaint().setColor(getColor(R.color.home_color));
                    break;
                case "work":
                    background.getPaint().setColor(getColor(R.color.work_color));
                    break;
                case "school":
                    background.getPaint().setColor(getColor(R.color.school_color));
                    break;
                case "errand":
                    background.getPaint().setColor(getColor(R.color.errands_color));
                    break;
                default:
                    background.getPaint().setColor(0x00000000);
                    break;
            }

            LayerDrawable homeAsUpDrawable = new LayerDrawable(new Drawable[] {background, menuIcon});

            Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(homeAsUpDrawable);

    }

    @Override
    public void sendFilterInput(String input) {

        if (!contextFilter.equals(input)) {
            contextFilter = input;
            updateFilter();
            sendInput(currentViewName);
        }
    }

    @Override
    public void sendInput(String input) {
        Log.d("MainActivity", "input " + input + " received");

        Fragment fragment = TodayTaskListFragment.newInstance(contextFilter);

        switch (input) {
            case "today":
                // Change to Today List View Fragment
                fragment = TodayTaskListFragment.newInstance(contextFilter);
                setTitle("Today, " + DateManager.getFormattedDate());
                break;
            case "tomorrow":
                // Change to Tomorrow List View Fragment
                fragment = TomorrowTaskListFragment.newInstance(contextFilter);
                setTitle("Tomorrow, " + DateManager.getTomorrowFormattedDate());
                break;
            case "pending":
                // Change to Pending List View Fragment
                fragment = PendingTaskListFragment.newInstance(contextFilter);
                setTitle("Pending");
                break;
            case "recurring":
                // currentFragment = "recurring";

                //Change to Recurring List View Fragment
                fragment = RecurringTaskListFragment.newInstance(contextFilter);
                setTitle("Recurring");
                break;
            default:
                Log.e("MainActivity", "No Valid View Found");
                return;
        }

        currentViewName = input;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        onCreateHelper();

        onStart();

        setContentView(view.getRoot());

        currentViewName = "today";

        sendInput(currentViewName);

    }
}
