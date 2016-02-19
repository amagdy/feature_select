package com.a1works.featureSelection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class ReaderBasedRecordProcessor implements RecordProcessor<Reader> {
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

    private BufferedReader bufferedReader = null;
    private String readLine(Reader inputReader) throws IOException {
        if (bufferedReader == null) {
            bufferedReader = new BufferedReader(inputReader);
        }
        String line = bufferedReader.readLine();
        if (line == null) {
            bufferedReader = null;
        }
        return line;
    }

    @Override
    public Iterable<Record> getRecords(final Reader inputReader) throws InvalidRecordFormatException {
        if (inputReader == null) {
            throw new IllegalArgumentException("Input Reader can not be null");
        }
        return new Iterable<Record>() {
            private String currentLine;
            @Override
            public Iterator<Record> iterator() {
                return new Iterator<Record>() {
                    @Override
                    public boolean hasNext() {
                        try {
                            currentLine = ReaderBasedRecordProcessor.this.readLine(inputReader);
                        } catch (IOException e) {
                            currentLine = null;
                            throw new RuntimeException(String.format("Could not read from Reader: %s", inputReader.toString()));
                        }
                        return currentLine != null;
                    }

                    @Override
                    public Record next() {
                        if (currentLine == null) {
                            return null;
                        }
                        currentLine = sanitizeRecordFormat(currentLine);
                        String[] recordParts = currentLine.split(" +");
                        MlClass cls = MlClass.getInstance(recordParts[0]);
                        Map<Feature, Long> featuresFrequencies = new HashMap<>(recordParts.length-1);
                        for (int i = 1; i < recordParts.length; i++) {
                            String[] featureAndFreq = recordParts[i].split(":");
                            Feature feature = Feature.getInstance(featureAndFreq[0]);
                            long freq = Long.parseLong(featureAndFreq[1]);
                            featuresFrequencies.put(feature, freq);
                        }
                        DefaultRecord record = new DefaultRecord(cls, featuresFrequencies);
                        return record;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("You cannot remove any items from this collection it is immutable.");
                    }
                };
            }
        };
    }


}
