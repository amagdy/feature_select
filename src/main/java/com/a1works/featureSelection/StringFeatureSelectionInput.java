package com.a1works.featureSelection;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public final class StringFeatureSelectionInput implements FeatureSelectionInput {

    private Map<Feature, Frequency> featuresFrequencies = new HashMap<>();
    private Map<MlClass, Map<Feature, Frequency>> featureFrequencyPerClass = new HashMap<>();
    private Map<MlClass, Frequency> classesFrequencies = new HashMap<>();
    private long numberOfAllRecords;

    private static final Pattern recordPattern = Pattern.compile("^[a-zA-Z0-9_]+( +[a-zA-Z0-9_]+:[0-9]+)+$");



    public static FeatureSelectionInput createInstance(String input) {
        if (input == null)
            throw new IllegalArgumentException("Input cannot be null.");

        Map<Feature, Frequency> featuresFrequencies = new HashMap<>();
        Map<MlClass, Map<Feature, Frequency>> featureFrequencyPerClass = new HashMap<>();
        Map<MlClass, Frequency> classesFrequencies = new HashMap<>();
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
        Map<Feature, Frequency> featuresFrequencies,
        Map<MlClass, Frequency> classesFrequencies,
        Map<MlClass, Map<Feature, Frequency>> featureFrequencyPerClass,
        String record
    ) {
        String[] recordParts = record.split(" +");
        MlClass cls = MlClass.getInstance(recordParts[0]);
        if (classesFrequencies.containsKey(cls)) {
            classesFrequencies.get(cls).incrementBy(1);
        } else {
            classesFrequencies.put(cls, new Frequency(1));
        }
        if (!featureFrequencyPerClass.containsKey(cls)) {
            featureFrequencyPerClass.put(cls, new HashMap<Feature, Frequency>());
        }

        for (int i = 1; i < recordParts.length; i++) {
            String[] featureAndFreq = recordParts[i].split(":");
            Feature feature = Feature.getInstance(featureAndFreq[0]);
            long freq = Long.valueOf(featureAndFreq[1]).longValue();
            if (featuresFrequencies.containsKey(feature)) {
                featuresFrequencies.get(feature).incrementBy(freq);
            } else {
                featuresFrequencies.put(feature, new Frequency(freq));
            }

            if (featureFrequencyPerClass.get(cls).containsKey(feature)) {
                featureFrequencyPerClass.get(cls).get(feature).incrementBy(freq);
            } else {
                featureFrequencyPerClass.get(cls).put(feature, new Frequency(freq));
            }
        }
    }


    @Override
    public Map<Feature, Frequency> getFeaturesFrequencies() {
        return featuresFrequencies;
    }

    @Override
    public Map<MlClass, Map<Feature, Frequency>> getFeatureFrequencyPerClass() {
        return featureFrequencyPerClass;
    }

    @Override
    public Map<MlClass, Frequency> getClassesFrequencies() {
        return classesFrequencies;
    }

    @Override
    public long getNumberOfAllRecords() {
        return numberOfAllRecords;
    }


}
