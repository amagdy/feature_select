package com.a1works.featureSelection;

import java.util.Map;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public interface FeatureSelectionInput {

    public Map<Feature, Frequency> getFeaturesFrequencies();

    public Map<MlClass, Map<Feature, Frequency>> getFeatureFrequencyPerClass();

    public Map<MlClass, Frequency> getClassesFrequencies();

    public long getNumberOfAllRecords();

}
