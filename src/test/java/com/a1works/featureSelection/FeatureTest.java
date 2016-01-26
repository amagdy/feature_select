package com.a1works.featureSelection;

import org.junit.Test;
import static org.junit.Assert.*;

public class FeatureTest {

    @Test
    public void checkIfTwoFeaturesWithTheSameNameAreEqual(){
        String featureName = "Feature1";
        Feature f1 = Feature.getInstance(featureName);
        Feature f2 = Feature.getInstance(new String(featureName));

        assertTrue("Feature with the same name must be equal.", f1.equals(f2));
    }

    @Test
    public void checkIfTwoFeaturesWithDifferentNamesAreNotEqual(){
        Feature f1 = Feature.getInstance("feature 1");
        Feature f2 = Feature.getInstance("feature 2");

        assertFalse("Features with different names must not be equal.", f1.equals(f2));
    }

    @Test
    public void checkFeatureIsNotEqualNull(){
        Feature f1 = Feature.getInstance("feature1");
        assertFalse("A feature cannot be equal to null.", f1.equals(null));
    }

    @Test
    public void testFeaturesWithEqualNameMustHaveEqualHashCode(){
        String featureName = "Feature1";
        Feature f1 = Feature.getInstance(featureName);
        Feature f2 = Feature.getInstance(new String(featureName));

        assertTrue("Features with equals names must have equal hashCodes.", f1.hashCode() == f2.hashCode());
    }

    @Test
    public void testFeaturesWithDifferentNamesMustHaveDifferentHashCodes(){
        Feature f1 = Feature.getInstance("Feature 1");
        Feature f2 = Feature.getInstance("Feature 2");

        assertTrue("Features with different names must have different hashCodes.", f1.hashCode() != f2.hashCode());
    }

}
