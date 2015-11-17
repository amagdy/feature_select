package com.a1works.featureSelection;

import org.junit.Test;

import static org.junit.Assert.*;

public class FrequencyTest {

//    @Test
//    public void testGetFrequency() {
//
//    }
//
//    @Test
//    public void testIncrementFrequency() {
//
//    }
//
//    @Test
//    public void testGetEvent() {
//
//    }

    @Test
    public void testEqualsNonEqualObjects() {
        Feature f1 = Feature.getInstance("feature1");
        Feature f2 = Feature.getInstance("feature2");

        Frequency<Feature> freq1 = new Frequency<>(f1);
        Frequency<Feature> freq2 = new Frequency<>(f2);

        assertNotEquals(freq1, freq2);
    }

    @Test
    public void testDifferentHashCodes() {
        Feature f1 = Feature.getInstance("feature1");
        Feature f2 = Feature.getInstance("feature2");

        Frequency<Feature> freq1 = new Frequency<>(f1);
        Frequency<Feature> freq2 = new Frequency<>(f2);

        assertNotEquals(freq1.hashCode(), freq2.hashCode());
    }
}