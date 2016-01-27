package com.a1works.featureSelection;

import java.util.ArrayList;
import java.util.Collections;
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
        allInputs.add(input2);
        if (inputs != null && inputs.length > 0) {
            for (Input input : inputs) {
                allInputs.add(input);
            }
        }
        MergedInput mergedInput = new MergedInput();
        for (Input input : allInputs) {
            for (Feature feature : input.getFeatures()) {
                mergedInput.appendFeature(feature, input.getFeatureFrequency(feature));
            }
            for (MlClass cls : input.getMlClasses()) {
                mergedInput.appendMlClass(cls, input.getMlClassFrequency(cls));
                for (Feature feature : input.getFeatures()) {
                    mergedInput.appendFeaturePerClass(cls, feature, input.getFeatureFrequencyPerClass(feature, cls));
                }
            }
            mergedInput.numberOfAllRecords += input.getRecordsCount();
        }
        return mergedInput;
    }

    private static class MergedInput implements Input {
        private Map<Feature, Frequency<Feature>> featuresFrequencies = new HashMap<>();
        private Map<MlClass, Map<Feature, Frequency<Feature>>> featureFrequencyPerClass = new HashMap<>();
        private Map<MlClass, Frequency<MlClass>> classesFrequencies = new HashMap<>();
        private long numberOfAllRecords = 0;

        private void appendMlClass(MlClass cls, long frequency){
            Frequency<MlClass> currentFrequency = classesFrequencies.get(cls);
            if (currentFrequency == null) {
                currentFrequency = new Frequency<>(cls);
                classesFrequencies.put(cls, currentFrequency);
            }
            currentFrequency.incrementFrequency(frequency);
        }

        private void appendFeature(Feature feature, long frequency){
            Frequency<Feature> currentFrequency = featuresFrequencies.get(feature);
            if (currentFrequency == null) {
                currentFrequency = new Frequency<>(feature);
                featuresFrequencies.put(feature, currentFrequency);
            }
            currentFrequency.incrementFrequency(frequency);
        }

        private void appendFeaturePerClass(MlClass cls, Feature feature, long frequency){
            Map<Feature, Frequency<Feature>> classFeaturesMap = featureFrequencyPerClass.get(cls);
            if (classFeaturesMap == null) {
                classFeaturesMap = new HashMap<>();
                featureFrequencyPerClass.put(cls, classFeaturesMap);
            }
            Frequency<Feature> featureFreq = classFeaturesMap.get(feature);
            if (featureFreq == null) {
                featureFreq = new Frequency<Feature>(feature);
                classFeaturesMap.put(feature, featureFreq);
            }
            featureFreq.incrementFrequency(frequency);
        }

        @Override
        public Set<Feature> getFeatures() {
            return Collections.unmodifiableSet(featuresFrequencies.keySet());
        }

        @Override
        public Set<MlClass> getMlClasses() {
            return Collections.unmodifiableSet(classesFrequencies.keySet());
        }

        @Override
        public long getFeatureFrequency(Feature feature) {
            Frequency<Feature> freq = featuresFrequencies.get(feature);
            if (freq == null) {
                return 0;
            }
            return freq.getFrequency();
        }

        @Override
        public long getMlClassFrequency(MlClass cls) {
            Frequency<MlClass> freq = classesFrequencies.get(cls);
            if (freq == null) {
                return 0;
            }
            return freq.getFrequency();
        }

        @Override
        public long getFeatureFrequencyPerClass(Feature feature, MlClass cls) {
            Map<Feature, Frequency<Feature>> featuresFrequenciesOfClass = featureFrequencyPerClass.get(cls);
            if (featuresFrequenciesOfClass == null) {
                return 0;
            }
            Frequency<Feature> freq = featuresFrequenciesOfClass.get(feature);
            if (freq == null) {
                return 0;
            }
            return freq.getFrequency();
        }


        @Override
        public long getRecordsCount() {
            return numberOfAllRecords;
        }

        @Override
        public Set<Feature> getMlClassFeatures(MlClass cls) {
            Map<Feature, Frequency<Feature>> featuresFrequenciesOfClass = featureFrequencyPerClass.get(cls);
            if (featuresFrequenciesOfClass == null) {
                return Collections.EMPTY_SET;
            }
            return Collections.unmodifiableSet(featuresFrequenciesOfClass.keySet());
        }
    }

}
