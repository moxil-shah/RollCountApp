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

public class EditSessionNameDialog extends AppCompatDialogFragment {

    private EditText edtTextInputNewSessionName;
    private EditSessionNameDialogListener listener;
    public EditSessionNameDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_dialog_fragment, null);

        builder.setView(view)
                .setTitle("New Game Session Name")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sessionName = edtTextInputNewSessionName.getText().toString();

                        listener.applyTexts(sessionName);
                    }
                });

        edtTextInputNewSessionName = view.findViewById(R.id.editTextInputNewSessionName);


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (EditSessionNameDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement EditSessionNameDialogListener");
        }

    }

    public interface EditSessionNameDialogListener {
        void applyTexts(String sessionName);
    }
}