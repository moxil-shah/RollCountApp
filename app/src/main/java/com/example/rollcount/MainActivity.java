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

// declaring required objects
    protected ListView gameSessionList;
    protected ArrayList<GameSession> dataList;
    protected ArrayAdapter<GameSession> gameSessionAdapter;
    protected Button btnAddSession, btnDeleteSession;
    protected int itemIndex;
    protected boolean itemDelete = false;
    protected TextView txtTotalCounter;

    // got help from https://www.youtube.com/watch?v=qO3FFuBrT2E
    // used to quite frankly "StartActivityForResult" and manage the data it sends back, which is a GameSession object
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult
            (new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == 1) { // matches with the result code in SessionActivity
                if (result.getData() != null) {
                    Bundle bundle = (Bundle) result.getData().getExtras();
                    // get the object, then replace the corresponding object in the listview with ot
                    GameSession gameSession = (GameSession) bundle.get("Game Session");
                    dataList.get(itemIndex).setSessionName(gameSession.getSessionName());
                    dataList.get(itemIndex).setNewGameTotals(gameSession.getGameTotals());
                    dataList.get(itemIndex).setGameOutcomes(gameSession.getGameOutcomes());
                    dataList.get(itemIndex).setDateStarted(gameSession.getDateStarted());
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

        // get data of app's previous state if there was one
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
                // delete or open a game session
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
                if (dataList.size() != 0) { // cannot delete nothing
                    Toast.makeText(MainActivity.this, "Click Session to Delete", Toast.LENGTH_LONG).show();
                    itemDelete = true;
                }
            }
        });
    }

    private void removeSession() {
        dataList.remove(itemIndex);
        gameSessionAdapter.notifyDataSetChanged(); // update list view
        txtTotalCounter.setText(String.format("Total = %s", String.valueOf(dataList.size())));
        itemDelete = false; // reset helper variable
        saveData(); // update saved state
    }

    // got help from https://www.youtube.com/watch?v=qO3FFuBrT2E
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
    // got help from https://www.youtube.com/watch?v=ARezg1D9Zd0
    // receive data for the new game session the user made
    public void applyNewGameSession(String sessionName, int numberOfRolls, int numberOfSides, String dateStarted) {
        GameSession createdNew = new GameSession(numberOfRolls, numberOfSides);
        createdNew.setSessionName(sessionName);
        createdNew.setDateStarted(dateStarted);
        dataList.add(createdNew);
        gameSessionList.setAdapter(gameSessionAdapter);
        txtTotalCounter.setText(String.format("Total = %s", String.valueOf(dataList.size())));
        gameSessionAdapter.notifyDataSetChanged();
        saveData();
    }

    // got help from https://www.youtube.com/watch?v=jcliHGR3CHo
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dataList);
        editor.putString("sessions", json);
        editor.apply();
    }

    // got help from https://www.youtube.com/watch?v=jcliHGR3CHo
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("sessions", null);
        Type type = new TypeToken<ArrayList<GameSession>>() {}.getType();
        dataList = gson.fromJson(json, type);
        if (dataList == null) { // if its first type opening app
            dataList = new ArrayList<>();
        }
    }
}