package com.example.rollcount;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddSessionDialog.AddSessionDialogListener{
    private static final String TAG ="Test";

// declaring required objects

    protected ListView gameSessionList;
    protected ArrayList<GameSession> dataList;
    protected ArrayAdapter<GameSession> gameSessionAdapter;
    protected Button btnAddSession, btnDeleteSession;
    protected int itemIndex;
    protected boolean itemDelete = false;
    protected TextView txtTotalCounter;


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
                    dataList.get(itemIndex).setGameOutcomes(gameSession.getGameOutcomes());
                    gameSessionList.setAdapter(gameSessionAdapter);
                    gameSessionAdapter.notifyDataSetChanged();
                    saveData();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameSession[] gameSessions = {};
        //dataList = new ArrayList<GameSession>();
        loadData();
        txtTotalCounter = (TextView) findViewById(R.id.textTotalCounter);
        txtTotalCounter.setText(String.format("Total = %s", String.valueOf(dataList.size())));

        // finding elements
        gameSessionList = findViewById(R.id.session_list);
        btnAddSession = (Button) findViewById(R.id.buttonAddSession);
        btnDeleteSession = (Button) findViewById(R.id.buttonDeleteSession);

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
                if (itemDelete)
                    removeSession();
                else {
                    openSessionActivity();
                }
            }
        });
        btnDeleteSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataList.size() != 0) {
                    Toast.makeText(MainActivity.this, "Click Session to Delete", Toast.LENGTH_LONG).show();
                    itemDelete = true;
                }
                else{
                    Toast.makeText(MainActivity.this, "Don't even try to crash my app", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void removeSession() {
        dataList.remove(itemIndex);
        gameSessionAdapter.notifyDataSetChanged(); // update list view
        txtTotalCounter.setText(String.format("Total = %s", String.valueOf(dataList.size())));
        itemDelete = false;
        saveData();
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
    public void applyTexts(String sessionName, int numberOfRolls, int numberOfSides, String dateStarted) {
        GameSession createdNew = new GameSession();
        createdNew.setSessionName(sessionName);
        createdNew.setDateStarted(dateStarted);
        createdNew.setNumberOfDiceRolls(numberOfRolls);
        createdNew.setNumberOfDiceSides(numberOfSides);
        createdNew.setGameTotals();
        dataList.add(createdNew);
        gameSessionList.setAdapter(gameSessionAdapter);
        txtTotalCounter.setText(String.format("Total = %s", String.valueOf(dataList.size())));
        gameSessionAdapter.notifyDataSetChanged();
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dataList);
        editor.putString("sessions", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("sessions", null);
        Type type = new TypeToken<ArrayList<GameSession>>() {}.getType();
        dataList = gson.fromJson(json, type);

        if (dataList == null) {
            dataList = new ArrayList<>();
        }
    }
}