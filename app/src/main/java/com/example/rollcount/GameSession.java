package com.example.rollcount;

import android.os.Build;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class GameSession implements Serializable {


    private final HashMap<Integer, Integer> gameTotals;
    private Calendar dateStarted;
    private final String stringDateStarted;
    private String sessionName;
    private int numberOfDiceRolls;
    private int numberOfDiceSides;
    private int sessionCounter;


    public GameSession() {
        sessionCounter += 1;
        dateStarted = Calendar.getInstance();
        stringDateStarted = DateFormat.getDateInstance(DateFormat.SHORT).format(dateStarted.getTime());
        setSessionName("Session #"+ sessionCounter);
        gameTotals = new HashMap<>();

    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public int getNumberOfDiceRolls() {
        return numberOfDiceRolls;
    }

    public void setNumberOfDiceRolls(int numberOfDiceRolls) {
        this.numberOfDiceRolls = numberOfDiceRolls;
    }

    public int getNumberOfDiceSides() {
        return numberOfDiceSides;
    }

    public void setNumberOfDiceSides(int numberOfDiceSides) {
        this.numberOfDiceSides = numberOfDiceSides;
    }

    public void setPossibleTotals() {
        for (int i = getNumberOfDiceRolls(); i < getNumberOfDiceRolls() * getNumberOfDiceSides(); i++) {
            gameTotals.put(i, 0);
        }
    }

    public void addADiceRoll(int diceRoll) {
        if (gameTotals.get(diceRoll) != null) {
            gameTotals.put(diceRoll, gameTotals.get(diceRoll) + 1);
        }

    }

    public int getSpecificGameTotal(int i) {
        if (gameTotals.get(i) != null) {
            return gameTotals.get(i);
        }
        return -1;
    }

    public Calendar getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Calendar dateStarted) {
        this.dateStarted = dateStarted;
    }

    @NonNull
    @Override
    public String toString()
    {
        return sessionName+" (N="+getNumberOfDiceRolls()+", M="+getNumberOfDiceSides()+")";
    }
}