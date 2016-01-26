package com.a1works.featureSelection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InputBuilder<T> {

    private T input;
    private RecordProcessor<T> recordProcessor;

    public static <T> InputBuilder<T> createInstance(T rawInput,
                                                                     RecordProcessor<T> processor) {
        if (rawInput == null)
            throw new IllegalArgumentException("input cannot be null.");
        InputBuilder builder = new InputBuilder();

        builder.recordProcessor = processor;
        builder.input = rawInput;

        return builder;
    }

    public Input build() {
        DefaultInput instance = new DefaultInput();
        Iterable<Record> records = recordProcessor.getRecords(input);
        for (Record record : records) {
            instance.appendRecord(record);
        }
        return instance;
    }

    private static class DefaultInput implements Input {

        private Map<Feature, Frequency<Feature>> featuresFrequencies = new HashMap<>();
        private Map<MlClass, Map<Feature, Frequency<Feature>>> featureFrequencyPerClass = new HashMap<>();
        private Map<MlClass, Frequency<MlClass>> classesFrequencies = new HashMap<>();
        private long numberOfAllRecords = 0;

        private DefaultInput() {
            featuresFrequencies = new HashMap<>();
            classesFrequencies = new HashMap<>();
            featureFrequencyPerClass = new HashMap<>();
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
