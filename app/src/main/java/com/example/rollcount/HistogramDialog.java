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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class HistogramDialog extends AppCompatDialogFragment {

    private GameSession openedGameSession;
    private TextView txtHistogram;
    private TextView txtHistogramStats;

    public HistogramDialog() {}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.histogram_dialog, null);
        txtHistogram = (TextView) view.findViewById(R.id.textHistogram);
        txtHistogramStats = (TextView) view.findViewById(R.id.textHistogramStats);

        Bundle mArgs = getArguments();
        if (mArgs != null) {
            openedGameSession = (GameSession) mArgs.get("Game Session");
            txtHistogram.setText(openedGameSession.getStringHistogram());
            txtHistogramStats.setText(openedGameSession.getStrHistogramStats());
        }
        builder.setView(view)
                .setTitle(openedGameSession.getSessionName()+" Histogram")
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openedGameSession.resetStringHistogram();
                    }
                });

        return builder.create();
    }
}