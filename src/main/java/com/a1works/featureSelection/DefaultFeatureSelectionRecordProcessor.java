package com.a1works.featureSelection;

import java.util.Set;

/**
 * Created by Ahmed_Magdy on 12/6/15.
 */
public class DefaultFeatureSelectionRecordProcessor implements FeatureSelectionRecordProcessor {
    @Override
    public Record extractRecord(String recordStr) throws InvalidRecordFormatException {
        return null;
    }


    @Override
    public FeatureSelectionInput mergeInputs(FeatureSelectionInput... inputs) {
        return null;
    }

    private static class MergedFeatureSelectionInput implements FeatureSelectionInput {

        private void appendRecord(Record record) {

        }

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
            return 0;
        }

        @Override
        public Set<Feature> getMlClassFeatures(MlClass cls) {
            return null;
        }
    }
}
