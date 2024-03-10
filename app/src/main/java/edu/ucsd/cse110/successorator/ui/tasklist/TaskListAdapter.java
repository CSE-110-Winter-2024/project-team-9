package edu.ucsd.cse110.successorator.ui.tasklist;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.GoalItemCardBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;

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
        // Get the task for this position.
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


        // Populate the view with the task's data.
        binding.taskText.setText(task.text());

        binding.taskLayout.setOnClickListener(v -> {
            assert task != null;
            onDeleteClick.accept(task);
        });

        ImageView imageView = binding.imageView;
        TextView contextInitial = binding.contextInitial;
        String context = task.category();

        switch(context) {
            case "home":
                imageView.setImageResource(R.drawable.home_icon);
                contextInitial.setText("H");
                break;
            case "work":
                imageView.setImageResource(R.drawable.work_icon);
                contextInitial.setText("W");
                break;
            case "school":
                imageView.setImageResource(R.drawable.school_icon);
                contextInitial.setText("S");
                break;
            case "errand":
                imageView.setImageResource(R.drawable.errand_icon);
                contextInitial.setText("E");
                break;
        }




        /*
        Sets strikethrough if the task is finished: https://stackoverflow.com/questions/9786544/creating-a-strikethrough-text
         */
        if (task.isFinished()) {
            binding.taskText.setPaintFlags(binding.taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            imageView.setImageResource(R.drawable.finished_icon);
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
