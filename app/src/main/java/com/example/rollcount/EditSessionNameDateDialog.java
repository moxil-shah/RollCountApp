package com.example.rollcount;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;


// (2) This citation is in regards to sending data from a dialog and making this dialog
// From whom: Coding in Flow
// Date published: Oct 5, 2017
// License: CC BY
// URL: https://www.youtube.com/watch?v=ARezg1D9Zd0
public class EditSessionNameDateDialog extends AppCompatDialogFragment {
    // declaring required objects
    private EditText edtTextInputNewSessionName;
    private EditText edtTextInputNewSessionDate;
    private EditSessionNameDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_dialog_fragment, null); // make the view the.xml file layout

        builder.setView(view)
                .setTitle("Edit Game Session")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sessionName = edtTextInputNewSessionName.getText().toString(); // new session name
                        String sessionDate = edtTextInputNewSessionDate.getText().toString(); // new session date
                        listener.applyTexts(sessionName, sessionDate); // update the session name by making a context listener. this gets sent back
                    }
                });

       edtTextInputNewSessionName = view.findViewById(R.id.editTextInputNewSessionName);
       edtTextInputNewSessionDate = view.findViewById(R.id.editTextInputNewSessionDate);
        return builder.create();
    }

    @Override
    // fragment is attached to its context
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (EditSessionNameDialogListener) context;
    }
    // send the data back to calling activity
    public interface EditSessionNameDialogListener {
        void applyTexts(String sessionName, String sessionDate);
    }
}