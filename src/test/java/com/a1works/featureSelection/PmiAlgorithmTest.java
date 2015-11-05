package com.a1works.featureSelection;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
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

    private FeatureSelectionInput getInputFromString(String data){
        return StringFeatureSelectionInput.createInstance(data);
    }


    @Test
    public void checkIfPmiCalculatesCorrectResultsForCorrectData(){
        FeatureSelectionAlgorithm target = new PmiAlgorithm();
        SortedSet<ScoredFeature> featuresScores = target.scoreFeatures(getInputFromString(INPUT_SMALL_DATA));
        assertNotNull("Features scores cannot be Null.", featuresScores);
    }

}
