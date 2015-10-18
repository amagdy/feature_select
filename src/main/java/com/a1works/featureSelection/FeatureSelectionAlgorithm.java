package com.a1works.featureSelection;

import java.util.SortedMap;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public interface FeatureSelectionAlgorithm {

    public SortedMap<Feature, Score> scoreFeatures(FeatureSelectionInput input);

}
