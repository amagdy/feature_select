package com.a1works.featureSelection;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public final class StringFeatureSelectionInput implements FeatureSelectionInput {

    private Map<Feature, Frequency<Feature>> featuresFrequencies = new HashMap<>();
    private Map<MlClass, Map<Feature, Frequency<Feature>>> featureFrequencyPerClass = new HashMap<>();
    private Map<MlClass, Frequency<MlClass>> classesFrequencies = new HashMap<>();
    private long numberOfAllRecords;

    private static final Pattern recordPattern = Pattern.compile("^[a-zA-Z0-9_]+( +[a-zA-Z0-9_]+:[0-9]+)+$");



    public static FeatureSelectionInput createInstance(String input) {
        if (input == null)
            throw new IllegalArgumentException("Input cannot be null.");

        Map<Feature, Frequency<Feature>> featuresFrequencies = new HashMap<>();
        Map<MlClass, Map<Feature, Frequency<Feature>>> featureFrequencyPerClass = new HashMap<>();
        Map<MlClass, Frequency<MlClass>> classesFrequencies = new HashMap<>();
        int recordsCount = 0;

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
            processOneRecord(featuresFrequencies, classesFrequencies, featureFrequencyPerClass, record);
            recordsCount++;
        }

        StringFeatureSelectionInput instance = new StringFeatureSelectionInput();
        instance.featuresFrequencies = featuresFrequencies;
        instance.classesFrequencies = classesFrequencies;
        instance.featureFrequencyPerClass = featureFrequencyPerClass;
        instance.numberOfAllRecords = recordsCount;
        return instance;
    }

    private StringFeatureSelectionInput() {}

    private static void processOneRecord(
        Map<Feature, Frequency<Feature>> featuresFrequencies,
        Map<MlClass, Frequency<MlClass>> classesFrequencies,
        Map<MlClass, Map<Feature, Frequency<Feature>>> featureFrequencyPerClass,
        String record
    ) {
        String[] recordParts = record.split(" +");
        MlClass cls = MlClass.getInstance(recordParts[0]);
        if (classesFrequencies.containsKey(cls)) {
            classesFrequencies.get(cls).incrementFrequency(1);
        } else {
            classesFrequencies.put(cls, new Frequency<MlClass>(cls, 1));
        }
        if (!featureFrequencyPerClass.containsKey(cls)) {
            featureFrequencyPerClass.put(cls, new HashMap<Feature, Frequency<Feature>>());
        }

        for (int i = 1; i < recordParts.length; i++) {
            String[] featureAndFreq = recordParts[i].split(":");
            Feature feature = Feature.getInstance(featureAndFreq[0]);
            long freq = Long.valueOf(featureAndFreq[1]).longValue();
            if (featuresFrequencies.containsKey(feature)) {
                featuresFrequencies.get(feature).incrementFrequency(freq);
            } else {
                featuresFrequencies.put(feature, new Frequency<Feature>(feature, freq));
            }

            if (featureFrequencyPerClass.get(cls).containsKey(feature)) {
                featureFrequencyPerClass.get(cls).get(feature).incrementFrequency(freq);
            } else {
                featureFrequencyPerClass.get(cls).put(feature, new Frequency<Feature>(feature, freq));
            }
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
    public long getFeatureFrequencyPerClass(Feature feature, MlClass cls) {
        Map<Feature, Frequency<Feature>> classFeatures = featureFrequencyPerClass.get(cls);
        if (classFeatures == null) return 0;
        Frequency<Feature> featureFrequency = featureFrequencyPerClass.get(cls).get(feature);
        if (featureFrequency == null) return 0;
        return featureFrequency.getFrequency();
    }

}
