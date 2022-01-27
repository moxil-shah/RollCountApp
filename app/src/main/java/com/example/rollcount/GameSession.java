package com.example.rollcount;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;


public class GameSession implements Serializable {

    private ArrayList<Integer> gameOutcomes;
    private String dateStarted;
    private HashMap<Integer, Integer> gameTotals;
    private String sessionName;
    private String strHistogramStats = "";
    private String strHistogram = "";
    private int numberOfDiceRolls;
    private int numberOfDiceSides;

    public GameSession() {
    }

    public void setGameTotals() {
        gameTotals = new HashMap<Integer, Integer>();
        gameOutcomes = new ArrayList<Integer>();
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
                strHistogram = strHistogram.concat(outcomeRepeated + String.valueOf(outcome) + " ");
                strHistogram = strHistogram.concat(frequencyRepeated + "(" + String.valueOf(frequency) + ") ");
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
                if (outcome > max) {
                    max = outcome;
                }
                if (outcome < min) {
                    min = outcome;
                }
            }
        }

        if (avgHelperVariable == 0) {
            strHistogramStats = strHistogramStats.concat("Min: " + "n/a" + "\n");
            strHistogramStats = strHistogramStats.concat("Max: " + "n/a" + "\n");
            strHistogramStats = strHistogramStats.concat("Avg: " + "n/a" + "\n");
            return strHistogramStats;
        } else {

            avg /= avgHelperVariable;
            avg = Math.round(avg * 100);
            avg = avg / 100;
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
            gameOutcomes.add(diceRoll);
        }
    }

    public void undoDiceRoll() {
        if (this.getGameOutcomes().size() != 0) {
            int deletedRoll = this.gameOutcomes.remove(getGameOutcomes().size() - 1);
            gameTotals.put(deletedRoll, gameTotals.get(deletedRoll) - 1);
        }
    }
    
    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String aDate) {

        if (aDate.equals("")) {
            this.dateStarted = String.valueOf(LocalDateTime.now()).substring(0, 10);
        } else {
            this.dateStarted = aDate;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return sessionName + " (N=" + getNumberOfDiceRolls() + ", " +
                this.dateStarted + ")";
    }

    public ArrayList<Integer> getGameOutcomes() {
        return gameOutcomes;
    }

    public void setGameOutcomes(ArrayList<Integer> gameOutcomes) {
        this.gameOutcomes = gameOutcomes;
    }
}