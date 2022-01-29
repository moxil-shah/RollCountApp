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

    // attributes of a GameSession object
    private ArrayList<Integer> gameOutcomes;
    private HashMap<Integer, Integer> gameTotals;
    private String dateStarted;
    private String sessionName;
    private String strHistogramStats = "";
    private String strHistogram = "";
    private final int numberOfDiceRolls;
    private final int numberOfDiceSides;

    // constructor method
    public GameSession(int numberOfRolls, int numberOfSides) {
        gameTotals = new HashMap<Integer, Integer>();
        gameOutcomes = new ArrayList<Integer>();
        this.numberOfDiceRolls = numberOfRolls;
        this.numberOfDiceSides = numberOfSides;
        this.setPossibleTotals();
    }

    // setter methods

    public void setGameOutcomes(ArrayList<Integer> gameOutcomes) {
        this.gameOutcomes = gameOutcomes;
    }

    public void setDateStarted(String aDate) {

        if (aDate.equals("")) {
            // help from https://www.geeksforgeeks.org/localdatetime-now-method-in-java-with-examples/#:~:text=now()%20method%20of%20a,obtain%20the%20current%20date%2Dtime.&text=Return%20value%3A%20This%20method%20returns,time%20using%20the%20system%20clock.
            this.dateStarted = String.valueOf(LocalDateTime.now()).substring(0, 10);
        } else {
            this.dateStarted = aDate;
        }
    }

    public void setNewGameTotals(HashMap<Integer, Integer> newGameTotals) {
        gameTotals = newGameTotals;
    }
    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public void setPossibleTotals() {

        for (int i = this.getNumberOfDiceRolls();
             i <= this.getNumberOfDiceRolls() * this.getNumberOfDiceSides(); i++) {
            gameTotals.put(i, 0);
        }
    }

    public void resetStringHistogram() {
        strHistogram = "";
        strHistogramStats = "";
    }

    public void addADiceRoll(int diceRoll) {
        if (gameTotals.get(diceRoll) != null) {
            gameTotals.put(diceRoll, gameTotals.get(diceRoll) + 1);
            gameOutcomes.add(diceRoll);
        }
    }

    public void undoDiceRoll() {
        if (this.getGameOutcomes().size() != 0) { // only undo if there is a roll to undo
            int deletedRoll = this.gameOutcomes.remove(getGameOutcomes().size() - 1);
            gameTotals.put(deletedRoll, gameTotals.get(deletedRoll) - 1);
        }
    }

    // getter methods

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
                if (outcome > max) { max = outcome;}
                if (outcome < min) { min = outcome;}
            }
        }
        if (avgHelperVariable == 0) {
            strHistogramStats = strHistogramStats.concat("Min: " + "n/a" + "\n");
            strHistogramStats = strHistogramStats.concat("Max: " + "n/a" + "\n");
            strHistogramStats = strHistogramStats.concat("Avg: " + "n/a" + "\n");
        }
        else {
            avg /= avgHelperVariable;
            // to round to 2 decimal places
            avg = Math.round(avg * 100);
            avg = avg / 100;
            strHistogramStats = strHistogramStats.concat("Min: " + String.valueOf(min) + "\n");
            strHistogramStats = strHistogramStats.concat("Max: " + String.valueOf(max) + "\n");
            strHistogramStats = strHistogramStats.concat("Avg: " + String.valueOf(avg) + "\n");
        }
        return strHistogramStats;
    }

    public int getNumberOfDiceRolls() {
        return numberOfDiceRolls;
    }

    public HashMap<Integer, Integer> getGameTotals() {
        return gameTotals;
    }

    public int getNumberOfDiceSides() { return numberOfDiceSides; }

    public ArrayList<Integer> getGameOutcomes() { return gameOutcomes; }

    public String getDateStarted() { return dateStarted; }

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
            // got help from https://stackoverflow.com/questions/1235179/simple-way-to-repeat-a-string
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

    @NonNull
    @Override
    public String toString() {
        return sessionName + " (N=" + getNumberOfDiceRolls() + ", " +
                this.dateStarted + ")";
    }
}