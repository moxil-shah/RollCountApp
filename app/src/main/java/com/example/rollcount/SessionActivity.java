package com.example.rollcount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SessionActivity extends AppCompatActivity implements EditSessionNameDateDialog.EditSessionNameDialogListener {

    // declaring required objects
    private Button btnEditSessionName, btnGoMainActivity, btnGoHistogram, btnUndo;
    private GameSession openedGameSession;
    private TextView sessionTitle;
    private ListView possibleRollsList;
    private ArrayList<Integer> possibleRollsDataList;
    private ArrayAdapter<Integer> possibleRollsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        possibleRollsDataList = new ArrayList<Integer>();
        // up to 3 dice * 6 sides = 18 possibilities
        for (int i = 1; i < 19; i++) {
            possibleRollsDataList.add(i);
        }
        possibleRollsList = findViewById(R.id.possibleRollsList);
        possibleRollsAdapter = new ArrayAdapter<Integer>(this, R.layout.content, possibleRollsDataList);
        possibleRollsList.setAdapter(possibleRollsAdapter);
        openedGameSession = (GameSession) getIntent().getSerializableExtra("GameSession"); // get the GameSession instance clicked
        sessionTitle = (TextView) findViewById(R.id.gameSessionName);
        sessionTitle.setText("Total rolls for "+openedGameSession.getSessionName()+": "+String.valueOf(openedGameSession.getGameOutcomes().size()));
        btnEditSessionName = (Button) findViewById(R.id.buttonEditSessionName);
        btnGoMainActivity = (Button) findViewById(R.id.buttonGoMainActivity);
        btnGoHistogram = (Button) findViewById(R.id.buttonHistogram);
        btnUndo = (Button) findViewById(R.id.buttonUndo);

        hideCertainButtons();

        btnEditSessionName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditDialog();
            }
        });
        btnGoMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeMainActivity();
            }
        });
        btnGoHistogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistogramDialog();
            }
        });
        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openedGameSession.undoDiceRoll();
                sessionTitle.setText("Total rolls for "+openedGameSession.getSessionName()+": "+String.valueOf(openedGameSession.getGameOutcomes().size()));
            }
        });
        possibleRollsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openedGameSession.addADiceRoll(possibleRollsDataList.get(i));
                sessionTitle.setText("Total rolls for "+openedGameSession.getSessionName()+": "+String.valueOf(openedGameSession.getGameOutcomes().size()));
            }
        });
    }

    private void openHistogramDialog() {
        // (2) This citation is in regards to sending data from a dialog and making this dialog
        // From whom: Coding in Flow
        // Date published: Oct 5, 2017
        // License: CC BY
        // URL: https://www.youtube.com/watch?v=ARezg1D9Zd0
        // receive new name from EditSessionNameDialog
        Intent intent = new Intent(this, HistogramDialog.class);
        intent.putExtra("Game Session", openedGameSession);
        Bundle args = (intent.getExtras());

        HistogramDialog histogramDialog = new HistogramDialog();
        histogramDialog.setArguments(args);
        histogramDialog.show(getSupportFragmentManager(), "histogram dialog");
    }

    private void hideCertainButtons() {
        // hide the buttons that do not associate with certain dice
        if (openedGameSession.getNumberOfDiceRolls() == 1) {
            for (int i = 0; i < 12; i++) {
                possibleRollsDataList.remove(6);
            }
        } else if (openedGameSession.getNumberOfDiceRolls() == 2) {
            for (int i = 0; i < 6; i++) {
                possibleRollsDataList.remove(12);
            }
            possibleRollsDataList.remove(0);
        } else {
            possibleRollsDataList.remove(0);
            possibleRollsDataList.remove(0);
        }
    }

    @Override
    public void onBackPressed() {
        resumeMainActivity();
    }

    public void resumeMainActivity() {
        // send the new object back to main activity
        // (4) This citation is in regards to intents and how to use them to send data to another activity
        // From whom: user914425
        // Date published: Sept 6, 2011
        // License: CCBY-SA
        // URL: https://stackoverflow.com/a/7325248
        Intent intent = new Intent(); // pass an object in intent with a tag to identify it when received
        intent.putExtra("Game Session", openedGameSession);
        setResult(1, intent); // Send this data to main screen
        SessionActivity.super.onBackPressed();
    }

    // open the edit dialog to make edits
    private void openEditDialog() {
        EditSessionNameDateDialog editSessionNameDialog = new EditSessionNameDateDialog();
        editSessionNameDialog.show(getSupportFragmentManager(), "edit dialog");
    }

    // (2) This citation is in regards to sending data from a dialog and making this dialog
    // From whom: Coding in Flow
    // Date published: Oct 5, 2017
    // License: CC BY
    // URL: https://www.youtube.com/watch?v=ARezg1D9Zd0
    // receive new name from EditSessionNameDialog
    @Override
    public void applyTexts(String sessionName, String sessionDate) {
        if (!sessionName.equals("")) {
            openedGameSession.setSessionName(sessionName);
            sessionTitle.setText("Total rolls for "+openedGameSession.getSessionName()+": "+String.valueOf(openedGameSession.getGameOutcomes().size()));
        }
        // current date if blank
        if (!sessionDate.equals("")) {
            openedGameSession.setDateStarted(sessionDate);
        }
    }
}