package com.a1works.featureSelection;

/**
 * Created by Ahmed_Magdy on 12/6/15.
 */
public interface FeatureSelectionInputBuilder {

    void setRecordProcessor(FeatureSelectionRecordProcessor processor);
    void appendInput(FeatureSelectionInput input);
    void appendRecord(String record);
    FeatureSelectionInput build();
}
