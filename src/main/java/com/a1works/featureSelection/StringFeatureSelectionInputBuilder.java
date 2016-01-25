package com.a1works.featureSelection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public final class StringFeatureSelectionInputBuilder implements FeatureSelectionInputBuilder {

    private static final Pattern recordPattern = Pattern.compile("^[a-zA-Z0-9_]+( +[a-zA-Z0-9_]+:[0-9]+)+$");

    private StringFeatureSelectionInput instance;
    private FeatureSelectionRecordProcessor recordProcessor;

    private StringFeatureSelectionInputBuilder(){}

    public static StringFeatureSelectionInputBuilder createInstance(String input) {
        if (input == null)
            throw new IllegalArgumentException("Input cannot be null.");

        StringFeatureSelectionInputBuilder builder = new StringFeatureSelectionInputBuilder();
        builder.instance = new StringFeatureSelectionInput();

        String[] records = input.split("\n");
        for (String record : records) {
            // for each record
            record = record.trim();
            if (record.equals("")) {
                continue;
            }
            if (!recordPattern.matcher(record).matches()) {
                throw new InvalidRecordFormatException("Invalid Record format.");
            }
            builder.instance.appendRecord(record);
        }
        return builder;
    }



    @Override
    public void setRecordProcessor(FeatureSelectionRecordProcessor processor) {
        recordProcessor = processor;
    }

    @Override
    public void appendInput(FeatureSelectionInput input) {
        instance.appendFeatureSelectionInput(input);
    }

    @Override
    public void appendRecord(String record) {
        instance.appendRecord(record);
    }

    @Override
    public FeatureSelectionInput build() {
        return instance;
    }


    private static class StringFeatureSelectionInput implements FeatureSelectionInput {

        private Map<Feature, Frequency<Feature>> featuresFrequencies = new HashMap<>();
        private Map<MlClass, Map<Feature, Frequency<Feature>>> featureFrequencyPerClass = new HashMap<>();
        private Map<MlClass, Frequency<MlClass>> classesFrequencies = new HashMap<>();
        private long numberOfAllRecords = 0;

        private StringFeatureSelectionInput() {
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

        private void appendRecord(String record) {
            int RECORDS_COUNT = 1;
            String[] recordParts = record.split(" +");
            MlClass cls = MlClass.getInstance(recordParts[0]);
            incrementClassFrequency(cls, RECORDS_COUNT);

            for (int i = 1; i < recordParts.length; i++) {
                String[] featureAndFreq = recordParts[i].split(":");
                Feature feature = Feature.getInstance(featureAndFreq[0]);
                long freq = Long.parseLong(featureAndFreq[1]);
                addFeature(feature, freq);
                addFeatureToClass(cls, feature, freq);
            }
            numberOfAllRecords += RECORDS_COUNT;
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

        private void addFeatures(Set<Frequency<Feature>> appendedFeaturesFrequencies){
            for (Frequency<Feature> freq : appendedFeaturesFrequencies) {
                addFeature(freq.getEvent(), freq.getFrequency());
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
