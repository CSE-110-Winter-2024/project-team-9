package edu.ucsd.cse110.successorator.ui.tasklist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.databinding.GoalItemCardBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.util.DateManager;

public class TaskListAdapter extends ArrayAdapter<Task> {
    Consumer<Task> onDeleteClick;

    public TaskListAdapter(Context context, List<Task> tasks, Consumer<Task> onDeleteClick) {
        // This sets a bunch of stuff internally, which we can access
        // with getContext() and getItem() for example.
        //
        // Also note that ArrayAdapter NEEDS a mutable List (ArrayList),
        // or it will crash!
        super(context, 0, new ArrayList<>(tasks));
        this.onDeleteClick = onDeleteClick;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the flashcard for this position.
        var task = getItem(position);
        assert task != null;

        // Check if a view is being reused...
        GoalItemCardBinding binding;
        if (convertView != null) {
            // if so, bind to it
            binding = GoalItemCardBinding.bind(convertView);
        } else {
            // otherwise inflate a new view from our layout XML.
            var layoutInflater = LayoutInflater.from(getContext());
            binding = GoalItemCardBinding.inflate(layoutInflater, parent, false);
        }


        // Populate the view with the flashcard's data.
        if (task.type().equals("pending") || task.type().equals("single-time")) {
            binding.taskText.setText(task.text());
        } else {
            String text = task.text() + ", " + task.type();
            if (task.type().equals("weekly")) {
                //get day of week
                String dayOfWeek = DateManager.getDayOfWeek(task.dateCreated());
                text += " on " + dayOfWeek;
            }
            if (task.type().equals("yearly")) {
                String dayOfYear = DateManager.getDateNoYear(task.dateCreated());
                text += " on " + dayOfYear;
            }
            binding.taskText.setText(text);
        }

        if (!task.type().equals("single-time")) {
            binding.taskLayout.setOnLongClickListener(v -> {
                assert task != null;
                onDeleteClick.accept(task);
                return false;
            });
        } else {
            binding.taskLayout.setOnClickListener(v -> {
                assert task != null;
                onDeleteClick.accept(task);
            });
        }

        /*
        Sets strikethrough if the task is finished: https://stackoverflow.com/questions/9786544/creating-a-strikethrough-text
         */
        if (task.isFinished()) {
            binding.taskText.setPaintFlags(binding.taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            binding.taskText.setPaintFlags(binding.taskText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        return binding.getRoot();
    }

    // The below methods aren't strictly necessary, usually.
    // But get in the habit of defining them because they never hurt
    // (as long as you have IDs for each item) and sometimes you need them.

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        var task = getItem(position);
        assert task != null;

        var id = task.id();
        assert id != null;

        return id;
    }
}
