package com.example.rollcount;

import android.os.Build;
import android.util.Log;

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
import java.util.Map;

public class GameSession implements Serializable {



    private ArrayList<Integer> outcomes;
    private Calendar dateStarted;
    private HashMap<Integer, Integer> gameTotals;
    private final String stringDateStarted;
    private String sessionName;
    private String strHistogramStats = "";
    private String strHistogram = "";
    private int numberOfDiceRolls;
    private int numberOfDiceSides;

    public GameSession() {

        dateStarted = Calendar.getInstance();
        stringDateStarted = DateFormat.getDateInstance(DateFormat.SHORT).format(dateStarted.getTime());

    }

    public void setGameTotals() {
        gameTotals = new HashMap<Integer, Integer>();
        this.setPossibleTotals();
    }

    public void resetStringHistogram() {
        strHistogram = "";
        strHistogramStats = "";
    }



    public String getStringHistogram() {

        int outcomeOffset = 0, frequencyOffset = 0;

        for (Map.Entry<Integer, Integer> entry : gameTotals.entrySet()) {
            Integer outcome = entry.getKey();
            Integer frequency = entry.getValue();
            if (String.valueOf(outcome).length() > outcomeOffset) {
                outcomeOffset = String.valueOf(outcome).length();
            }
            if (String.valueOf(frequency).length() > frequencyOffset) {
                frequencyOffset = String.valueOf(frequency).length();
            }
        }

        for (Map.Entry<Integer, Integer> entry : gameTotals.entrySet()) {

            Integer outcome = entry.getKey();
            Integer frequency = entry.getValue();
            // cite below
            String outcomeRepeated = new String(new char[outcomeOffset - String.valueOf(outcome).length()]).replace("\0", "  ");
            String frequencyRepeated = new String(new char[frequencyOffset - String.valueOf(frequency).length()]).replace("\0", "  ");
            String hashRepeated = new String(new char[frequency]).replace("\0", "#");
            if (frequency != 0) {
                strHistogram = strHistogram.concat(outcomeRepeated+String.valueOf(outcome)+" ");
                strHistogram = strHistogram.concat(frequencyRepeated+"(" + String.valueOf(frequency) + ") ");
                strHistogram = strHistogram.concat(hashRepeated + "\n");
            }
        }
        return strHistogram;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getStrHistogramStats() {

        int min = 18, max = 1, avgHelperVariable = 0;
        double avg = 0.0;
        for (Map.Entry<Integer, Integer> entry : gameTotals.entrySet()) {

            Integer outcome = entry.getKey();
            Integer frequency = entry.getValue();
            if (frequency != 0) {
                avgHelperVariable += frequency;
                avg += outcome * frequency;
                if (outcome > max) {max = outcome;}
                if (outcome < min) {min = outcome;}
            }
        }

        if (avgHelperVariable == 0) {
            strHistogramStats = strHistogramStats.concat("Min: "+"n/a"+"\n");
            strHistogramStats = strHistogramStats.concat("Max: "+"n/a"+"\n");
            strHistogramStats = strHistogramStats.concat("Avg: "+"n/a"+"\n");
            return strHistogramStats;
        }
        else {

            avg /= avgHelperVariable;
            strHistogramStats = strHistogramStats.concat("Min: " + String.valueOf(min) + "\n");
            strHistogramStats = strHistogramStats.concat("Max: " + String.valueOf(max) + "\n");
            strHistogramStats = strHistogramStats.concat("Avg: " + String.valueOf(avg) + "\n");
            return strHistogramStats;
        }
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public int getNumberOfDiceRolls() {
        return numberOfDiceRolls;
    }

    public void setNewGameTotals(HashMap<Integer, Integer> newGameTotals) {
        gameTotals = newGameTotals;
    }

    public HashMap<Integer, Integer> getGameTotals() {
        return gameTotals;
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

        for (int i = this.getNumberOfDiceRolls(); i <= this.getNumberOfDiceRolls() * this.getNumberOfDiceSides(); i++) {
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
    public String toString() {
        return sessionName + " (N=" + getNumberOfDiceRolls() + ", M=" + getNumberOfDiceSides() + ")";
    }
}