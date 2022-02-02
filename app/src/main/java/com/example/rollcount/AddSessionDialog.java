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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
// (2) This citation is in regards to sending data from a dialog and making this dialog
// From whom: Coding in Flow
// Date published: Oct 5, 2017
// License: CC BY
// URL: https://www.youtube.com/watch?v=ARezg1D9Zd0

// this class serves as the dialog to adding a game session.
// simple dialog design.
// no outstanding issues.
public class AddSessionDialog extends AppCompatDialogFragment {
    // declaring required objects
    private EditText edtTextInputSessionName, edtTextInputRolls, edtTextInputDate;
    private AddSessionDialogListener listener;
    private Button btnOkay;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment, null);
        btnOkay = (Button) view.findViewById(R.id.buttonOkay);
        edtTextInputRolls = (EditText) view.findViewById(R.id.editTextInputRolls);
        edtTextInputSessionName = (EditText) view.findViewById(R.id.editTextInputSessionName);
        edtTextInputDate = (EditText) view.findViewById(R.id.editTextInputDate);
        builder.setView(view)
                .setTitle("New Game Session")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // all the stuff the user determines (except the sides, which is 6)
                String sessionName = edtTextInputSessionName.getText().toString();
                String numberOfRolls = edtTextInputRolls.getText().toString();

                //  if the user inputs an invalid roll, make toast to tell them
                if (!(numberOfRolls.equals("1") || numberOfRolls.equals("2") || numberOfRolls.equals("3"))){
                    Toast.makeText(getActivity(), "Enter valid roll between 1-3", Toast.LENGTH_SHORT).show();
                    return;
                }
                String numberOfSides = "6";
                String sessionDate = edtTextInputDate.getText().toString();

                final int numberOfRollsInt = Integer.parseInt(numberOfRolls);
                final int numberOfSidesInt = Integer.parseInt(numberOfSides);
                listener.applyNewGameSession(sessionName, numberOfRollsInt, numberOfSidesInt, sessionDate);
                // update the session name by making a context listener. this gets sent back
            }
        });
    return builder.create();
    }

    @Override
    // fragment is attached to its context
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (AddSessionDialogListener) context;
    }
    // send this data back to calling activity
    public interface AddSessionDialogListener {
        void applyNewGameSession(String sessionName, int numberOfRollsInt, int numberOfSidesInt, String dateStarted);

    }
}