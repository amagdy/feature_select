package com.a1works.featureSelection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FeatureSelectionInputBuilder<T> {

    private T input;
    private FeatureSelectionRecordProcessor<T> recordProcessor;

    public static <T> FeatureSelectionInputBuilder<T> createInstance(T rawInput,
                                                                     FeatureSelectionRecordProcessor<T> processor) {
        if (rawInput == null)
            throw new IllegalArgumentException("input cannot be null.");
        FeatureSelectionInputBuilder builder = new FeatureSelectionInputBuilder();

        builder.recordProcessor = processor;
        builder.input = rawInput;

        return builder;
    }

    public FeatureSelectionInput mergeInputs(FeatureSelectionInput input1,
                                             FeatureSelectionInput input2,
                                             FeatureSelectionInput... inputs) {
        if (input1 == null || input2 == null) {
            throw new IllegalArgumentException("Please provide at least 2 inputs");
        }
        DefaultFeatureSelectionInput accumulator = new DefaultFeatureSelectionInput(input1);
        accumulator.appendFeatureSelectionInput(input2);
        if (inputs != null && inputs.length > 0) {
            for (FeatureSelectionInput input : inputs) {
                accumulator.appendFeatureSelectionInput(input);
            }
        }
        return accumulator;
    }

    public FeatureSelectionInput build() {
        DefaultFeatureSelectionInput instance = new DefaultFeatureSelectionInput();
        Iterable<Record> records = recordProcessor.getRecords(input);
        for (Record record : records) {
            instance.appendRecord(record);
        }
        return instance;
    }

    private static class DefaultFeatureSelectionInput implements FeatureSelectionInput {

        private Map<Feature, Frequency<Feature>> featuresFrequencies = new HashMap<>();
        private Map<MlClass, Map<Feature, Frequency<Feature>>> featureFrequencyPerClass = new HashMap<>();
        private Map<MlClass, Frequency<MlClass>> classesFrequencies = new HashMap<>();
        private long numberOfAllRecords = 0;

        private DefaultFeatureSelectionInput() {
            featuresFrequencies = new HashMap<>();
            classesFrequencies = new HashMap<>();
            featureFrequencyPerClass = new HashMap<>();
        }

        private DefaultFeatureSelectionInput(FeatureSelectionInput input) {
            this();
            for (MlClass cls : input.getMlClasses()){
                classesFrequencies.put(cls, new Frequency<MlClass>(cls, input.getMlClassFrequency(cls)));
                Map<Feature, Frequency<Feature>> mapMlClassFeaturesFreq = new HashMap<>();
                featureFrequencyPerClass.put(cls, mapMlClassFeaturesFreq);
                for (Feature feature : input.getMlClassFeatures(cls)) {
                    mapMlClassFeaturesFreq.put(feature, new Frequency<Feature>(feature, input.getFeatureFrequencyPerClass(feature, cls)));
                }
            }
            for (Feature feature : input.getFeatures()) {
                featuresFrequencies.put(feature, new Frequency<Feature>(feature, input.getFeatureFrequency(feature)));
            }
        }

        @Override
        public Set<Feature> getFeatures() {
            return featuresFrequencies.keySet();
        }

        @Override
        public Set<MlClass> getMlClasses() {
            return classesFrequencies.keySet();
        }

        @Override
        public long getFeatureFrequency(Feature feature) {
            return featuresFrequencies.get(feature).getFrequency();
        }

        @Override
        public long getMlClassFrequency(MlClass cls) {
            return classesFrequencies.get(cls).getFrequency();
        }

        @Override
        public long getRecordsCount() {
            return numberOfAllRecords;
        }

        @Override
        public Set<Feature> getMlClassFeatures(MlClass cls) {
            Map<Feature, Frequency<Feature>> classFeaturesFrequencies = featureFrequencyPerClass.get(cls);
            if (classFeaturesFrequencies != null) {
                return Collections.unmodifiableSet(classFeaturesFrequencies.keySet());
            } else {
                return Collections.emptySet();
            }
        }

        @Override
        public long getFeatureFrequencyPerClass(Feature feature, MlClass cls) {
            Map<Feature, Frequency<Feature>> classFeatures = featureFrequencyPerClass.get(cls);
            if (classFeatures == null) return 0;
            Frequency<Feature> featureFrequency = featureFrequencyPerClass.get(cls).get(feature);
            if (featureFrequency == null) return 0;
            return featureFrequency.getFrequency();
        }

        private void appendRecord(Record record) {
            MlClass cls = record.getMlClass();
            incrementClassFrequency(cls, 1);
            for (Feature feature : record.getFeatures()) {
                long freq = record.getFeatureFrequency(feature);
                addFeature(feature, freq);
                addFeatureToClass(cls, feature, freq);
            }
            numberOfAllRecords++;
        }

        private void appendFeatureSelectionInput(FeatureSelectionInput input) {
            for (Feature feature : input.getFeatures()) {
                addFeature(feature, input.getFeatureFrequency(feature));
            }
            for (MlClass cls : input.getMlClasses()) {
                incrementClassFrequency(cls, input.getMlClassFrequency(cls));
                Set<Feature> classFeatures = input.getMlClassFeatures(cls);
                for (Feature feature : classFeatures) {
                    addFeatureToClass(cls, feature, input.getFeatureFrequencyPerClass(feature, cls));
                }
            }
            numberOfAllRecords += input.getRecordsCount();
        }

        private void incrementClassFrequency(MlClass cls, long frequency){
            if (classesFrequencies.containsKey(cls)) {
                classesFrequencies.get(cls).incrementFrequency(frequency);
            } else {
                classesFrequencies.put(cls, new Frequency<MlClass>(cls, frequency));
            }
        }

        private void addFeature(Feature feature, long frequency){
            if (featuresFrequencies.containsKey(feature)) {
                featuresFrequencies.get(feature).incrementFrequency(frequency);
            } else {
                featuresFrequencies.put(feature, new Frequency<Feature>(feature, frequency));
            }
        }

        private void addFeatureToClass(MlClass cls, Feature feature, long frequency){
            Map<Feature, Frequency<Feature>> clsFeaturesFrequencies = featureFrequencyPerClass.get(cls);
            if (clsFeaturesFrequencies == null) {
                clsFeaturesFrequencies = new HashMap<Feature, Frequency<Feature>>();
                featureFrequencyPerClass.put(cls, clsFeaturesFrequencies);
            }
            Frequency<Feature> featureFreq = clsFeaturesFrequencies.get(feature);
            if (featureFreq == null) {
                featureFreq = new Frequency<Feature>(feature, frequency);
                clsFeaturesFrequencies.put(feature, featureFreq);
            } else {
                featureFreq.incrementFrequency(featureFreq.getFrequency());
            }
        }

    }

}
