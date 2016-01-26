package com.a1works.featureSelection;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.SortedSet;

public class PmiAlgorithmTest {

    private static final int CORRECT_ALL_RECORDS_COUNT = 10;
    private static final String INPUT_SMALL_DATA = "1 1:1 2:1 3:1 5:1\n" +
            "1 1:1 3:1 6:1\n" +
            "1 1:1 2:1 3:1 7:1\n" +
            "1 1:1 3:1 8:1\n" +
            "1 1:1 2:1 3:1 9:1\n" +
            "0 1:1 4:1 10:1\n" +
            "0 1:1 2:1 4:1 11:1\n" +
            "0 1:1 4:1 12:1\n" +
            "0 1:1 2:1 4:1 13:1\n" +
            "0 1:1 4:1 14:1";

    private Input getInputFromString(String data){
        return InputBuilder.createInstance(data, new StringRecordProcessor()).build();
    }


    @Test
    public void checkIfPmiCalculatesCorrectResultsForCorrectData(){
        Algorithm target = new PmiAlgorithm();
        SortedSet<ScoredFeature> featuresScores = target.scoreFeatures(getInputFromString(INPUT_SMALL_DATA));
        assertNotNull("Features scores cannot be Null.", featuresScores);
    }

}
