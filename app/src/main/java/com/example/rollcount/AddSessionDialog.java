package com.example.rollcount;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
// got help from https://www.youtube.com/watch?v=ARezg1D9Zd0
public class AddSessionDialog extends AppCompatDialogFragment {
    // declaring required objects
    private EditText edtTextInputSessionName, edtTextInputRolls, edtTextInputDate;
    private AddSessionDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment, null);

        builder.setView(view)
                .setTitle("New Game Session")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // all the stuff the user determines (except the sides, which is 6)
                        String sessionName = edtTextInputSessionName.getText().toString();
                        String numberOfRolls = edtTextInputRolls.getText().toString();
                        String numberOfSides = "6";
                        String sessionDate = edtTextInputDate.getText().toString();
                        final int numberOfRollsInt = Integer.parseInt(numberOfRolls);
                        final int numberOfSidesInt = Integer.parseInt(numberOfSides);
                        listener.applyNewGameSession(sessionName, numberOfRollsInt, numberOfSidesInt, sessionDate);
                        // update the session name by making a context listener. this gets sent back
                    }
                });
    edtTextInputRolls = view.findViewById(R.id.editTextInputRolls);
    edtTextInputSessionName = view.findViewById(R.id.editTextInputSessionName);
    edtTextInputDate = view.findViewById(R.id.editTextInputDate);


    return builder.create();
    }

    @Override
    // fragment is attached to its context
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddSessionDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddSessionDialogListener");
        }

    }
    // send this data back to calling activity
    public interface AddSessionDialogListener {
        void applyNewGameSession(String sessionName, int numberOfRollsInt, int numberOfSidesInt, String dateStarted);
    }
}