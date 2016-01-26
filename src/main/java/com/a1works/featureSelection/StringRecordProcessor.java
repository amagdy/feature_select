package com.a1works.featureSelection;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class StringRecordProcessor implements RecordProcessor<String> {
    private static final String STR_PATTERN = "^[a-zA-Z0-9_-]+( +[a-zA-Z0-9_-]+:[0-9]+)+$";
    private static final Pattern RECORD_PATTERN = Pattern.compile(STR_PATTERN);

    private String sanitizeRecordFormat(String strRecord) throws InvalidRecordFormatException {
        if (strRecord == null) {
            throw new InvalidRecordFormatException("Record cannot be null.");
        }
        strRecord = strRecord.trim();
        if (!RECORD_PATTERN.matcher(strRecord).matches()) {
            throw new InvalidRecordFormatException(String.format("Record must have 1 class and 1 or more features, and every feature is associated with its frequency (int) in colon seperated pairs, according to this pattern /%s/ Record=[%s]", STR_PATTERN, strRecord));
        }
        return strRecord;
    }

    @Override
    public Iterable<Record> getRecords(String input) throws InvalidRecordFormatException {
        if (input == null) {
            throw new InvalidRecordFormatException("Record cannot be null");
        }
        List<Record> records = null;
        String[] arrRecords = input.split(" *(\r\n|\n|\r) *");
        for (String strRecord : arrRecords) {
            strRecord = sanitizeRecordFormat(strRecord);
            String[] recordParts = strRecord.split(" +");
            MlClass cls = MlClass.getInstance(recordParts[0]);
            Map<Feature, Long> featuresFrequencies = new HashMap<>(recordParts.length-1);
            for (int i = 1; i < recordParts.length; i++) {
                String[] featureAndFreq = recordParts[i].split(":");
                Feature feature = Feature.getInstance(featureAndFreq[0]);
                long freq = Long.parseLong(featureAndFreq[1]);
                featuresFrequencies.put(feature, freq);
            }
            DefaultRecord record = new DefaultRecord(cls, featuresFrequencies);
            if (records == null) {
                records = new LinkedList<>();
            }
            records.add(record);
        }
        if (records == null) {
            return Collections.EMPTY_LIST;
        } else {
            return Collections.unmodifiableList(records);
        }
    }
}
