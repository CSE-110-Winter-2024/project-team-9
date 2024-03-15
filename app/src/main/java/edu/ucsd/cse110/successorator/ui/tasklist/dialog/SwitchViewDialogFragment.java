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
import edu.ucsd.cse110.successorator.databinding.FragmentSwitchViewDialogBinding;

public class SwitchViewDialogFragment extends DialogFragment {

    private MainViewModel activityModel;
    private FragmentSwitchViewDialogBinding view;
    public interface OnInputListener {
        void sendInput(String input);
    }
    private OnInputListener myListener;

    SwitchViewDialogFragment() {
        
    }
    
    public static SwitchViewDialogFragment newInstance() {
        return new SwitchViewDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

        try {
            myListener = (OnInputListener) getActivity();
        } catch (ClassCastException error) {
            Log.e("Error", "onCreate: ClassCastException: " + error.getMessage());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentSwitchViewDialogBinding.inflate(getLayoutInflater());

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Switch Views")
                .setMessage("Which list would you like to view?")
                .setView(view.getRoot())
                .create();

        view.todayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClick(alertDialog, "today");
            }
        });

        view.tomorrowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClick(alertDialog, "tomorrow");
            }
        });

        view.pendingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClick(alertDialog, "pending");
            }
        });

        view.recurringButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClick(alertDialog, "recurring");
            }
        });

        return alertDialog;
    }

    public void buttonClick(AlertDialog alertDialog, String s) {
        alertDialog.dismiss();
        myListener.sendInput(s);
    }
}
