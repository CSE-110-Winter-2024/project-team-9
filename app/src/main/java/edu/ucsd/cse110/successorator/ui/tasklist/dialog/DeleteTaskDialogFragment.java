package edu.ucsd.cse110.successorator.ui.tasklist.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.MovePendingTaskBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.util.DateManager;

public class DeleteTaskDialogFragment extends DialogFragment {

    private MainViewModel activityModel;
    private MovePendingTaskBinding view;
    private Task task;


    DeleteTaskDialogFragment(Task task) {
        this.task = task;
    }

    public static DeleteTaskDialogFragment newInstance(Task task) {
        return new DeleteTaskDialogFragment(task);
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
        this.view = MovePendingTaskBinding.inflate(getLayoutInflater());

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("move pending task")
                .setMessage("Which list would you like to move this task to??")
                .setView(view.getRoot())
                .create();

        view.moveToToday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){buttonClick(alertDialog, "today");}
            //call some function to change task from pending to today
        });

        view.moveToTomorrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClick(alertDialog, "tomorrow");
            }
            //call some function to change task from pending to tomorrow
        });

        view.moveToFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClick(alertDialog, "finish");
            }
            //call some function to change task from pending to finished (move to today and cross it off)
        });

        view.moveToDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClick(alertDialog, "delete");
            }
            //call some function to delete task
        });

        return alertDialog;
    }

    public void buttonClick(AlertDialog alertDialog, String s) {
        alertDialog.dismiss();
        if (s.equals("today")) {
            activityModel.remove(task.id());
            activityModel.prepend(new Task(task.id(), task.text(), 1, task.isFinished(), DateManager.getGlobalDate().getDate(), task.category(),"single-time"));
        }

        if (s.equals("tomorrow")) {
            activityModel.remove(task.id());
            activityModel.prepend(new Task(task.id(), task.text(), 1, task.isFinished(), DateManager.getGlobalDate().getTomorrow(), task.category(),"single-time"));
        }

        if (s.equals("finish")) {
            activityModel.remove(task.id());
            activityModel.prepend(new Task(task.id(), task.text(), 1, true, DateManager.getGlobalDate().getDate(), task.category(),"single-time"));
        }

        if (s.equals("delete")) {
            activityModel.remove(task.id());
        }
    }
}
