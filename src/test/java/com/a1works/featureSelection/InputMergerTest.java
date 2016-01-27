package com.a1works.featureSelection;


import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class InputMergerTest {

    private Map<Feature, Long> featuresFromArray(String[] featuresNames, long numOfRecords){
        Map<Feature, Long> features = new HashMap<>(featuresNames.length);
        for (String featureName : featuresNames) {
            features.put(Feature.getInstance(featureName), numOfRecords);
        }
        return features;
    }

    private Map<MlClass, Long> classesFromArray(String[] classesNames, long numOfRecords){
        Map<MlClass, Long> classes = new HashMap<>(classesNames.length);
        for (String className : classesNames) {
            classes.put(MlClass.getInstance(className), numOfRecords / classesNames.length);
        }
        return classes;
    }

    private Map<MlClass, Map<Feature, Long>> featuresPerClassFrequencies(Map<MlClass, Long> classes, Map<Feature, Long> features){
        Map<MlClass, Map<Feature, Long>> featuresPerClass = new HashMap<>(classes.size());
        for (MlClass cls : classes.keySet()) {
            Map<Feature, Long> featurePerThisClass = new HashMap<>(features.size());
            Long classFreq = classes.get(cls);
            for (Feature feature : features.keySet()) {
                featurePerThisClass.put(feature, classFreq);
            }
            featuresPerClass.put(cls, featurePerThisClass);
        }
        return featuresPerClass;
    }

    private long getLongValueIfExists(Long val){
        if (val == null) {
            return 0;
        }
        return val.longValue();
    }

    private Input generateInput(final String[] classesNames, final String[] featuresNames, final long numOfRecords) {
        final Map<MlClass, Long> classes = classesFromArray(classesNames, numOfRecords);
        final Map<Feature, Long> features = featuresFromArray(featuresNames, numOfRecords);
        final Map<MlClass, Map<Feature, Long>> featuresPerClasses = featuresPerClassFrequencies(classes, features);

        Input input = new Input() {
            @Override
            public Set<Feature> getFeatures() {
                return Collections.unmodifiableSet(features.keySet());
            }

            @Override
            public Set<MlClass> getMlClasses() {
                return Collections.unmodifiableSet(classes.keySet());
            }

            @Override
            public long getFeatureFrequency(Feature feature) {
                return getLongValueIfExists(features.get(feature));
            }

            @Override
            public long getMlClassFrequency(MlClass cls) {
                return getLongValueIfExists(classes.get(cls));
            }

            @Override
            public long getFeatureFrequencyPerClass(Feature feature, MlClass cls) {
                Map<Feature, Long> featuresFrequenciesPerOneClass = featuresPerClasses.get(cls);
                if (featuresFrequenciesPerOneClass == null) {
                    return 0;
                }
                return getLongValueIfExists(featuresFrequenciesPerOneClass.get(feature));
            }

            @Override
            public long getRecordsCount() {
                return numOfRecords;
            }

            @Override
            public Set<Feature> getMlClassFeatures(MlClass cls) {
                return Collections.unmodifiableSet(featuresPerClasses.get(cls).keySet());
            }
        };
        return input;
    }

    private Input input1, input2, input3;
    private InputMerger merger;

    @Before
    public void setup(){
        input1 = generateInput(new String[]{"1", "2"}, new String[]{"a", "b", "c"}, 10);
        input2 = generateInput(new String[]{"1", "3"}, new String[]{"a", "d", "c"}, 11);
        input3 = generateInput(new String[]{"2", "4"}, new String[]{"a", "b", "e"}, 12);
        merger = InputMerger.getInstance();
    }

    @Test
    public void givenThreeInputsWhenMergedThierRecordCountsAreSummedUp(){
        Input target = merger.mergeInputs(input1, input2, input3);
        long sum = input1.getRecordsCount() + input2.getRecordsCount() + input3.getRecordsCount();
        assertEquals("When 3 input objects are merged the resulting record count must be equal to their sum", sum, target.getRecordsCount());
    }

    @Test
    public void givenThreeInputsWhenMergedTheirClassFrequenciesAreSummedUp(){
        MlClass cls = MlClass.getInstance("1");
        Input target = merger.mergeInputs(input1, input2, input3);
        long sum = input1.getMlClassFrequency(cls) + input2.getMlClassFrequency(cls) + input3.getMlClassFrequency(cls);
        assertEquals("When 3 input objects are merged the resulting class frequency of a certain class must be equal to their sum", sum, target.getMlClassFrequency(cls));
    }

    @Test
    public void givenThreeInputsWhenMergedTheirFeatureFrequenciesAreSummedUp(){
        Feature feature = Feature.getInstance("a");
        Input target = merger.mergeInputs(input1, input2, input3);
        long sum = input1.getFeatureFrequency(feature) + input2.getFeatureFrequency(feature) + input3.getFeatureFrequency(feature);
        assertEquals("When 3 input objects are merged the resulting feature frequency of a certain class must be equal to their sum", sum, target.getFeatureFrequency(feature));
    }

    @Test
    public void givenThreeInputsWhenMergedTheirFeatureFrequenciesPerClassAreSummedUp(){
        MlClass cls = MlClass.getInstance("1");
        Feature feature = Feature.getInstance("a");
        Input target = merger.mergeInputs(input1, input2, input3);
        long sum = input1.getFeatureFrequencyPerClass(feature, cls) + input2.getFeatureFrequencyPerClass(feature, cls) + input3.getFeatureFrequencyPerClass(feature, cls);
        assertEquals("When 3 input objects are merged the resulting feature frequency per class of a certain feature and class must be equal to their sum", sum, target.getFeatureFrequencyPerClass(feature, cls));
    }
}

