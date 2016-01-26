package com.a1works.featureSelection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by magdy on 26.01.16.
 */
public class InputMerger {
    private static final InputMerger INSTANCE = new InputMerger();
    public static InputMerger getInstance(){
        return INSTANCE;
    }

    public Input mergeInputs(Input input1,
                             Input input2,
                             Input... inputs){
        if (input1 == null || input2 == null) {
            throw new IllegalArgumentException("Please provide at least 2 inputs");
        }
        List<Input> allInputs = new ArrayList<>();
        allInputs.add(input1);
        allInputs.add(input1);
        if (inputs != null && inputs.length > 0) {
            for (Input input : inputs) {
                allInputs.add(input);
            }
        }
        MergedInput mergedInput = new MergedInput();
        for (Input input : allInputs) {
            mergedInput.numberOfAllRecords += input.getRecordsCount();
        }
        return mergedInput;
    }

    private static class MergedInput implements Input {
        private Map<Feature, Frequency<Feature>> featuresFrequencies = new HashMap<>();
        private Map<MlClass, Map<Feature, Frequency<Feature>>> featureFrequencyPerClass = new HashMap<>();
        private Map<MlClass, Frequency<MlClass>> classesFrequencies = new HashMap<>();
        private long numberOfAllRecords = 0;

        @Override
        public Set<Feature> getFeatures() {
            return null;
        }

        @Override
        public Set<MlClass> getMlClasses() {
            return null;
        }

        @Override
        public long getFeatureFrequency(Feature feature) {
            return 0;
        }

        @Override
        public long getMlClassFrequency(MlClass cls) {
            return 0;
        }

        @Override
        public long getFeatureFrequencyPerClass(Feature feature, MlClass cls) {
            return 0;
        }

        @Override
        public long getRecordsCount() {
            return numberOfAllRecords;
        }

        @Override
        public Set<Feature> getMlClassFeatures(MlClass cls) {
            return null;
        }
    }

}
