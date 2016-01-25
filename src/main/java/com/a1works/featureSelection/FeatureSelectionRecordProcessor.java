package com.a1works.featureSelection;

/**
 * Created by Ahmed_Magdy on 12/6/15.
 */
public interface FeatureSelectionRecordProcessor {

    Record extractRecord(String recordStr) throws InvalidRecordFormatException;

    FeatureSelectionInput mergeInputs(FeatureSelectionInput... inputs);
}
