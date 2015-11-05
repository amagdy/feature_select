package com.a1works.featureSelection;

import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public class StringFeatureSelectionInputTest {

    @Test
    public void givenOneLineWhenParseThenItShouldWork(){
        MlClass cls = MlClass.getInstance("1");
        FeatureSelectionInput target = StringFeatureSelectionInput.createInstance("1 1:1 2:1 3:1");
        assertEquals("Number of records must be 1.", 1, target.getRecordsCount());
        Set<MlClass> classes = target.getMlClasses();
        assertEquals("Number of classes must be 1.", 1, classes.size());
        assertTrue("First class name should be '1'.", classes.contains(cls));
        assertEquals("Number of features must be 3.", 3, target.getFeatures().size());
    }

    @Test
    public void given10LinesWhenParseThenItShouldWork(){
        FeatureSelectionInput target = StringFeatureSelectionInput.createInstance(
                "1 1:1 2:1 3:1 5:1\n" +
                "1 1:1 3:1 6:1\n" +
                "1 1:1 2:1 3:1 7:1\n" +
                "1 1:1 3:1 8:1\n" +
                "1 1:1 2:1 3:1 9:1\n" +
                "0 1:1 4:1 10:1\n" +
                "0 1:1 2:1 4:1 11:1\n" +
                "0 1:1 4:1 12:1\n" +
                "0 1:1 2:1 4:1 13:1\n" +
                "0 1:1 4:1 14:1");
        assertEquals(10, target.getRecordsCount());
        assertEquals(2, target.getMlClasses().size());
        assertEquals(14, target.getFeatures().size());
    }

    @Test(expected = InvalidRecordFormatException.class)
    public void givenInvalidStringWhenParseThenThrowException(){
        FeatureSelectionInput target = StringFeatureSelectionInput.createInstance("dsdsdsdsddsds");

    }

    @Test(expected = IllegalArgumentException.class)
    public void givenNullStringWhenParseThenThrowException(){
        FeatureSelectionInput target = StringFeatureSelectionInput.createInstance(null);

    }

}
