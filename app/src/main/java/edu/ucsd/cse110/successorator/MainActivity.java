package edu.ucsd.cse110.successorator;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import edu.ucsd.cse110.successorator.data.db.TaskDao;
import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.domain.DateTracker;
import edu.ucsd.cse110.successorator.ui.tasklist.TaskListFragment;
import edu.ucsd.cse110.successorator.ui.tasklist.dialog.AddTaskDialogFragment;
import edu.ucsd.cse110.successorator.ui.tasklist.dialog.SwitchViewDialogFragment;
import edu.ucsd.cse110.successorator.util.DateManager;

public class MainActivity extends AppCompatActivity implements SwitchViewDialogFragment.OnInputListener {

    private ActivityMainBinding view;

    private boolean isShowingList = true;

    private TaskDao taskDao;

    public static LocalDateTime lastOpened;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("task", MODE_PRIVATE);

        DateTracker dateTracker = new DateTracker(LocalDate.now());
        DateManager.initializeGlobalDate(dateTracker);
        setTitle(DateManager.getFormattedDate());


        DateManager.getLocalDateSubject().observe(localDate -> {
            //Log.d("main", "observer of local date changed");
            //Log.d("date manager date", DateManager.getFormattedDate());
            if (localDate == null) return;
            setTitle(DateManager.getFormattedDate());
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_bar, menu);
        return true;
    }

    //Defines functionality for when menu item "item" is pressed
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();

        if (itemId == R.id.header_bar_dropdown) {
            var dialogFragment = SwitchViewDialogFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            dialogFragment.show(fm, "SwitchViewDialogFragment");
        }

        //if item is the add task button, run initiateAddTask()
        if (itemId == R.id.header_bar_add_task) {
            // shows the AddTaskDialogBox fragment when + button is clicked
            var dialogFragment = AddTaskDialogFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            dialogFragment.show(fm, "AddTaskDialogFragment");
        }

        return super.onOptionsItemSelected(item);
    }


    private void addTask(String input) {
        System.out.println("add task: " + input);
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

    @Override
    public void sendInput(String input) {
        Log.d("MainActivity", "input " + input + " received");

        Fragment fragment = TaskListFragment.newInstance();

        switch (input) {
            case "today":
                Log.d("MainActivity", "Switch to today list");
                fragment = TaskListFragment.newInstance();
                break;
            case "tomorrow":
                Log.d("MainActivity", "Switch to tomorrow list");
                fragment = TaskListFragment.newInstance();
                break;
            case "pending":
                Log.d("MainActivity", "Switch to pending list");
                fragment = TaskListFragment.newInstance();
                break;
            case "recurring":
                Log.d("MainActivity", "Switch to recurring list");
                fragment = AddTaskDialogFragment.newInstance();
                break;
            default:
                Log.e("MainActivity", "No Valid View Found");
                fragment = TaskListFragment.newInstance();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }



}
