package com.example.rollcount;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddSessionDialog.AddSessionDialogListener{
    private static final String TAG ="Test";

// declaring required objects

    protected ListView gameSessionList;
    protected ArrayList<GameSession> dataList;
    protected ArrayAdapter<GameSession> gameSessionAdapter;
    protected Button btnAddSession;
    protected int itemIndex;


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult
            (new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == 1) {
                if (result.getData() != null) {
                    Bundle bundle = (Bundle) result.getData().getExtras();
                    GameSession gameSession = (GameSession) bundle.get("Game Session");
                    dataList.get(itemIndex).setSessionName(gameSession.getSessionName());
                    dataList.get(itemIndex).setNewGameTotals(gameSession.getGameTotals());
                    gameSessionList.setAdapter(gameSessionAdapter);
                    gameSessionAdapter.notifyDataSetChanged();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameSession[] gameSessions = {};
        dataList = new ArrayList<GameSession>();

        // finding elements
        gameSessionList = findViewById(R.id.session_list);
        btnAddSession = (Button) findViewById(R.id.buttonAddSession);

        // Setting ArrayAdapter
        gameSessionAdapter = new ArrayAdapter<GameSession>(this, R.layout.content, dataList);
        // Setting up frontend to take advantage of adapter object
        gameSessionList.setAdapter(gameSessionAdapter);



        btnAddSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
                }
        });
        gameSessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemIndex = i;
                openSessionActivity();
            }
        });
    }

    public void openSessionActivity() {
        GameSession openedGameSession = dataList.get(itemIndex);
        Intent intent = new Intent(this, SessionActivity.class);
        intent.putExtra("GameSession", openedGameSession);
        activityResultLauncher.launch(intent);

    }

    private void openDialog() {
        AddSessionDialog addSessionDialog = new AddSessionDialog();
        addSessionDialog.show(getSupportFragmentManager(), "add dialog");
    }

    @Override
    public void applyTexts(String sessionName, int numberOfRolls, int numberOfSides) {
        GameSession createdNew = new GameSession();
        createdNew.setSessionName(sessionName);
        createdNew.setNumberOfDiceRolls(numberOfRolls);
        createdNew.setNumberOfDiceSides(numberOfSides);
        createdNew.setGameTotals();
       // createdNew.setPossibleTotals();
        dataList.add(createdNew);
        gameSessionList.setAdapter(gameSessionAdapter);
        gameSessionAdapter.notifyDataSetChanged();

    }

}