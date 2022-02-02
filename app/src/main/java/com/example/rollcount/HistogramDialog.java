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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
// (2) This citation is in regards to sending data from a dialog and making this dialog
// From whom: Coding in Flow
// Date published: Oct 5, 2017
// License: CC BY
// URL: https://www.youtube.com/watch?v=ARezg1D9Zd0
public class HistogramDialog extends AppCompatDialogFragment {
    // declaring required objects
    private GameSession openedGameSession;
    private TextView txtHistogram;
    private TextView txtHistogramStats;
    private Histogram gameHistogram;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.histogram_dialog, null);
        txtHistogram = (TextView) view.findViewById(R.id.textHistogram);
        txtHistogramStats = (TextView) view.findViewById(R.id.textHistogramStats);

        // (5) This citation is in regards to sending data in the form of a bundle
        // From whom: JafarKhQ
        // Date published: March 17, 2013
        // License: CCBY-SA
        // URL: https://stackoverflow.com/a/15459259

        // get the GameSession object passed in the bundle
        Bundle passedGameSessionObject = getArguments();
        if (passedGameSessionObject != null) {
            openedGameSession = (GameSession) passedGameSessionObject.get("Game Session");
            gameHistogram = new Histogram(); // new histogram object which i use to make the stats and histogram, give the hashmap of all rolls
            txtHistogram.setText(gameHistogram.getStringHistogram(openedGameSession.getGameTotals()));
            txtHistogramStats.setText(gameHistogram.getStrHistogramStats(openedGameSession.getGameTotals()));
        }
        builder.setView(view)
                .setTitle(openedGameSession.getSessionName()+" Histogram")
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return builder.create();
    }
}