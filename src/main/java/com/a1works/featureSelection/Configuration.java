package com.a1works.featureSelection;

/**
 * Created by Ahmed_Magdy on 12/6/15.
 */
public interface Configuration {

    FeatureSelectionRecordProcessor getRecordProcessor();

    FeatureSelectionInputBuilder getFeatureSelectionInputBuilder();

    FeatureSelectionAlgorithm getFeatureSelectionAlgorithm();
    
}
