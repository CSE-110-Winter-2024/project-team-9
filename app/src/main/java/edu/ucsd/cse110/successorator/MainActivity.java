package edu.ucsd.cse110.successorator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding view;
    private Calendar currCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE M/dd");
        String formattedDate = dateFormatter.format(currCalendar.getTime());
        setTitle(formattedDate);

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
            initiateAddTask();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initiateAddTask() {
        System.out.println("add task");
    }
}
