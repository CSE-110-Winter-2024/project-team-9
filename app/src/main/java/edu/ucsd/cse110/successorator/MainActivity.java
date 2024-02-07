package edu.ucsd.cse110.successorator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.addtask.AddTaskFragment;
import edu.ucsd.cse110.successorator.ui.tasklist.TaskListFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding view;
    private boolean isShowingList = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);

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
            swapFragments();
        }

        return super.onOptionsItemSelected(item);
    }

    private void swapFragments() {
        if (isShowingList) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, AddTaskFragment.newInstance())
                    .commit();
        } else {
            addTask("new title");
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, TaskListFragment.newInstance())
                    .commit();
        }

        isShowingList = !isShowingList;
    }

    private void addTask(String input) {
        System.out.println("add task: " + input);
    }
}
