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

public class AddSessionDialog extends AppCompatDialogFragment {

    private EditText edtTextInputSessionName, edtTextInputRolls, edtTextInputDate;
    private AddSessionDialogListener listener;
    public AddSessionDialog() {
    }

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
                        String sessionName = edtTextInputSessionName.getText().toString();
                        String numberOfRolls = edtTextInputRolls.getText().toString();
                        String numberOfSides = "6";
                        String sessionDate = edtTextInputDate.getText().toString();
                        final int numberOfRollsInt = Integer.parseInt(numberOfRolls);
                        final int numberOfSidesInt = Integer.parseInt(numberOfSides);
                        listener.applyTexts(sessionName, numberOfRollsInt, numberOfSidesInt, sessionDate);
                    }
                });
    edtTextInputRolls = view.findViewById(R.id.editTextInputRolls);
    edtTextInputSessionName = view.findViewById(R.id.editTextInputSessionName);
    edtTextInputDate = view.findViewById(R.id.editTextInputDate);


    return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddSessionDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddSessionDialogListener");
        }

    }

    public interface AddSessionDialogListener {
        void applyTexts(String sessionName, int numberOfRollsInt, int numberOfSidesInt, String dateStarted);
    }
}