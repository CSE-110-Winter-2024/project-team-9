package edu.ucsd.cse110.successorator;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.tasklist.dialog.AddTaskDialogFragment;
import edu.ucsd.cse110.successorator.util.DateManager;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding view;
//    private Calendar currCalendar;
    private boolean isShowingList = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        DateManager.initializeGlobalDate(this);
        setTitle(DateManager.getFormattedDate());

        DateManager.getLocalDateSubject().observe(localDate -> {
            //Log.d("main", "observer oflocal date changed");
            if (localDate == null) return;
            setTitle(DateManager.getFormattedDate());
        });

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
}
