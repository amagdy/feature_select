package com.a1works.featureSelection;


import java.util.Collections;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class InputMergerTest {

    private Input generateInput() {
        Input input = new Input() {
            @Override
            public Set<Feature> getFeatures() {
                return Collections.EMPTY_SET;
            }

            @Override
            public Set<MlClass> getMlClasses() {
                return Collections.EMPTY_SET;
            }

            @Override
            public long getFeatureFrequency(Feature feature) {
                return 1;
            }

            @Override
            public long getMlClassFrequency(MlClass cls) {
                return 1;
            }

            @Override
            public long getFeatureFrequencyPerClass(Feature feature, MlClass cls) {
                return 1;
            }

            @Override
            public long getRecordsCount() {
                return 10;
            }

            @Override
            public Set<Feature> getMlClassFeatures(MlClass cls) {
                return Collections.EMPTY_SET;
            }
        };
        return input;
    }

    private Input input1, input2, input3;
    private InputMerger merger;

    @Before
    public void setup(){
        input1 = generateInput();
        input2 = generateInput();
        input3 = generateInput();
        merger = InputMerger.getInstance();
    }

    @Test
    public void givenThreeInputsWhenMergedThierRecordCountsAreSummedUp(){
        Input target = merger.mergeInputs(input1, input2, input3);
        assertEquals("When 3 input objects are merged the resulting record count must be equal to their sum", 30, target.getRecordsCount());
    }

    @Test
    public void givenThreeInputsWhenMergedThierClassFrequenciesAreSummedUp(){
        MlClass cls = MlClass.getInstance("1");
        Input target = merger.mergeInputs(input1, input2, input3);
        long sum = input1.getMlClassFrequency(cls) + input2.getMlClassFrequency(cls) + input3.getMlClassFrequency(cls);
        assertEquals("When 3 input objects are merged the resulting class frequency of a certain class must be equal to their sum", sum, target.getMlClassFrequency(cls));
    }

    @Test
    public void givenThreeInputsWhenMergedThierFeatureFrequenciesAreSummedUp(){
        Feature feature = Feature.getInstance("a");
        Input target = merger.mergeInputs(input1, input2, input3);
        long sum = input1.getFeatureFrequency(feature) + input2.getFeatureFrequency(feature) + input3.getFeatureFrequency(feature);
        assertEquals("When 3 input objects are merged the resulting feature frequency of a certain class must be equal to their sum", sum, target.getFeatureFrequency(feature));
    }

    @Test
    public void givenThreeInputsWhenMergedThierFeatureFrequenciesPerClassAreSummedUp(){
        MlClass cls = MlClass.getInstance("1");
        Feature feature = Feature.getInstance("a");
        Input target = merger.mergeInputs(input1, input2, input3);
        long sum = input1.getFeatureFrequencyPerClass(feature, cls) + input2.getFeatureFrequencyPerClass(feature, cls) + input3.getFeatureFrequencyPerClass(feature, cls);
        assertEquals("When 3 input objects are merged the resulting feature frequency per class of a certain feature and class must be equal to their sum", sum, target.getFeatureFrequencyPerClass(feature, cls));
    }

}

