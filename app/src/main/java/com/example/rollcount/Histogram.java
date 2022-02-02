package com.example.rollcount;

import java.util.HashMap;
import java.util.Map;

public class Histogram {

    private String strHistogram = "";
    private String strHistogramStats = "";

    // getters

    public String getStrHistogramStats(HashMap <Integer, Integer> gameTotals) {

        int min = 18, max = 1, avgHelperVariable = 0;
        double avg = 0.0;
        for (Map.Entry<Integer, Integer> entry : gameTotals.entrySet()) {
            Integer outcome = entry.getKey();
            Integer frequency = entry.getValue();
            if (frequency != 0) {
                avgHelperVariable += frequency;
                avg += outcome * frequency;
                if (outcome > max) { max = outcome;} // find max
                if (outcome < min) { min = outcome;} // find min
            }
        }
        // if there is no data to make a histogram
        if (avgHelperVariable == 0) {
            strHistogramStats = strHistogramStats.concat("Min: " + "n/a" + "\n");
            strHistogramStats = strHistogramStats.concat("Max: " + "n/a" + "\n");
            strHistogramStats = strHistogramStats.concat("Avg: " + "n/a" + "\n");
        }

        // if there is some data
        else {
            avg /= avgHelperVariable;
            // to round to 2 decimal places
            avg = Math.round(avg * 100);
            avg = avg / 100;
            strHistogramStats = strHistogramStats.concat("Min: " + min + "\n");
            strHistogramStats = strHistogramStats.concat("Max: " + max + "\n");
            strHistogramStats = strHistogramStats.concat("Avg: " + avg + "\n");
        }
        return strHistogramStats;
    }

    public String getStringHistogram(HashMap <Integer, Integer> gameTotals) {

        int outcomeOffset = 0, frequencyOffset = 0; // used to align each row
        for (Map.Entry<Integer, Integer> entry : gameTotals.entrySet()) {
            Integer outcome = entry.getKey();
            Integer frequency = entry.getValue();
            // find the lengthiest frequency and outcome and align on that
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
            // (6) This citation is in regards to the character repetition below
            // From whom: user102008
            // Date published: Feb 4, 2011
            // License: CC BY-SA 3.0
            // URL: https://stackoverflow.com/a/4903603
            String outcomeRepeated = new String(new char[outcomeOffset - String.valueOf(outcome).length()]).replace("\0", "  ");
            String frequencyRepeated = new String(new char[frequencyOffset - String.valueOf(frequency).length()]).replace("\0", "  ");
            String hashRepeated = new String(new char[frequency]).replace("\0", "#");
            if (frequency != 0) {
                strHistogram = strHistogram.concat(outcomeRepeated + outcome + " ");
                strHistogram = strHistogram.concat(frequencyRepeated + "(" + frequency + ") ");
                strHistogram = strHistogram.concat(hashRepeated + "\n");
            }
        }
        return strHistogram;
    }
}
