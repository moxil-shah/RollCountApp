package com.example.rollcount;

import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

// this class serves as the blueprint for an instance of any and every game session.
// has getters and setters to set and get all attributes that pertain to a game session and dice rolls.
// no outstanding issues.
public class GameSession implements Serializable {

    // attributes of a GameSession object
    private ArrayList<Integer> gameOutcomes; // used to undo rolls
    private HashMap<Integer, Integer> gameTotals; // used to store rolls
    private String dateStarted;
    private String sessionName;
    private final int numberOfDiceRolls;
    private final int numberOfDiceSides;


    // constructor method
    public GameSession(int numberOfRolls, int numberOfSides) {
        gameTotals = new HashMap<Integer, Integer>();
        gameOutcomes = new ArrayList<Integer>();
        this.numberOfDiceRolls = numberOfRolls;
        this.numberOfDiceSides = numberOfSides;
        // add all possible rolls with a frequency of 0
        for (int i = this.numberOfDiceRolls; i <= this.numberOfDiceRolls * this.numberOfDiceSides; i++) {
            gameTotals.put(i, 0);
        }
    }

    // setter methods

    public void setGameOutcomes(ArrayList<Integer> aGameOutcomes) {
        this.gameOutcomes = aGameOutcomes;
    }

    public void setDateStarted(String aDate) {

        // (7) This citation is in regards to getting the current data below
        // From whom: no author
        // Date published: Jan 21, 2019
        // License: CCBY-SA
        // URL: https://www.geeksforgeeks.org/localdatetime-now-method-in-java-with-examples/#:~:text=now()%20method%20of%20a,obtain%20the%20current%20date%2Dtime.&text=Return%20value%3A%20This%20method%20returns,time%20using%20the%20system%20clock
        if (aDate.equals("")) {
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

    public int getNumberOfDiceRolls() {
        return numberOfDiceRolls;
    }

    public HashMap<Integer, Integer> getGameTotals() {
        return gameTotals;
    }

    public int getNumberOfDiceSides() { return numberOfDiceSides; }

    public ArrayList<Integer> getGameOutcomes() { return gameOutcomes; }

    public String getDateStarted() { return dateStarted; }

    @NonNull
    @Override
    // string representation in the listview in the main activity
    public String toString() {
        return sessionName + " (Dice: " + getNumberOfDiceRolls() + "d" +getNumberOfDiceSides()
                + ")\n" + "Date: " +
                this.dateStarted + "\nTotal Rolls: " + String.valueOf(getGameOutcomes().size());
    }
}