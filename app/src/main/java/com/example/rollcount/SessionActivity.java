package com.example.rollcount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SessionActivity extends AppCompatActivity implements EditSessionNameDialog.EditSessionNameDialogListener, View.OnClickListener {

    protected Button btnEditSessionName, btnGoMainActivity, btnGoHistogram, btnUndo ,btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18;
    protected GameSession openedGameSession;
    protected TextView sessionTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        openedGameSession = (GameSession)getIntent().getSerializableExtra("GameSession");

        sessionTitle = (TextView) findViewById(R.id.textView1);
        sessionTitle.setText(openedGameSession.getSessionName());

        btnEditSessionName = (Button) findViewById(R.id.buttonEditSessionName);
        btnGoMainActivity = (Button) findViewById(R.id.buttonGoMainActivity);
        btnGoHistogram = (Button) findViewById(R.id.buttonHistogram);
        btnUndo = (Button) findViewById(R.id.buttonUndo);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btn10 = (Button) findViewById(R.id.button10);
        btn11 = (Button) findViewById(R.id.button11);
        btn12 = (Button) findViewById(R.id.button12);
        btn13 = (Button) findViewById(R.id.button13);
        btn14 = (Button) findViewById(R.id.button14);
        btn15 = (Button) findViewById(R.id.button15);
        btn16 = (Button) findViewById(R.id.button16);
        btn17 = (Button) findViewById(R.id.button17);
        btn18 = (Button) findViewById(R.id.button18);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn16.setOnClickListener(this);
        btn17.setOnClickListener(this);
        btn18.setOnClickListener(this);

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
            }
        });

    }

    private void openHistogramDialog() {

        Intent intent = new Intent(this, HistogramDialog.class);
        intent.putExtra("Game Session", openedGameSession);
        Bundle args = (intent.getExtras());

        HistogramDialog histogramDialog = new HistogramDialog();
        histogramDialog.setArguments(args);
        histogramDialog.show(getSupportFragmentManager(), "histogram dialog");
    }

    private void hideCertainButtons() {
        if (openedGameSession.getNumberOfDiceRolls() == 1) {
            btn7.setVisibility(View.GONE);
            btn8.setVisibility(View.GONE);
            btn9.setVisibility(View.GONE);
            btn10.setVisibility(View.GONE);
            btn11.setVisibility(View.GONE);
            btn12.setVisibility(View.GONE);
            btn13.setVisibility(View.GONE);
            btn14.setVisibility(View.GONE);
            btn15.setVisibility(View.GONE);
            btn16.setVisibility(View.GONE);
            btn17.setVisibility(View.GONE);
            btn18.setVisibility(View.GONE);
        }
        else if (openedGameSession.getNumberOfDiceRolls() == 2) {
            btn1.setVisibility(View.GONE);
            btn13.setVisibility(View.GONE);
            btn14.setVisibility(View.GONE);
            btn15.setVisibility(View.GONE);
            btn16.setVisibility(View.GONE);
            btn17.setVisibility(View.GONE);
            btn18.setVisibility(View.GONE);
        }
        else {
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
        }

    }

    public void resumeMainActivity() {
        Intent intent = new Intent();
        intent.putExtra("Game Session", openedGameSession); //value should be your string from the edittext
        setResult(1, intent); //The data you want to send back
        SessionActivity.super.onBackPressed();
    }

    private void openEditDialog() {
        EditSessionNameDialog editSessionNameDialog = new EditSessionNameDialog();
        editSessionNameDialog.show(getSupportFragmentManager(), "edit dialog");
    }

    @Override
    public void applyTexts(String sessionName) {
        openedGameSession.setSessionName(sessionName);
        sessionTitle.setText(sessionName);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button1:
                openedGameSession.addADiceRoll(1);
                break;
            case R.id.button2:
                openedGameSession.addADiceRoll(2);
                break;
            case R.id.button3:
                openedGameSession.addADiceRoll(3);
                break;
            case R.id.button4:
                openedGameSession.addADiceRoll(4);
                break;
            case R.id.button5:
                openedGameSession.addADiceRoll(5);
                break;
            case R.id.button6:
                openedGameSession.addADiceRoll(6);
                break;
            case R.id.button7:
                openedGameSession.addADiceRoll(7);
                break;
            case R.id.button8:
                openedGameSession.addADiceRoll(8);
                break;
            case R.id.button9:
                openedGameSession.addADiceRoll(9);
                break;
            case R.id.button10:
                openedGameSession.addADiceRoll(10);
                break;
            case R.id.button11:
                openedGameSession.addADiceRoll(11);
                break;
            case R.id.button12:
                openedGameSession.addADiceRoll(12);
                break;
            case R.id.button13:
                openedGameSession.addADiceRoll(13);
                break;
            case R.id.button14:
                openedGameSession.addADiceRoll(14);
                break;
            case R.id.button15:
                openedGameSession.addADiceRoll(15);
                break;
            case R.id.button16:
                openedGameSession.addADiceRoll(16);
                break;
            case R.id.button17:
                openedGameSession.addADiceRoll(17);
                break;
            case R.id.button18:
                openedGameSession.addADiceRoll(18);
                break;
        }
    }
}