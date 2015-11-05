package com.a1works.featureSelection;

import java.util.SortedSet;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public interface FeatureSelectionAlgorithm {

    public SortedSet<ScoredFeature> scoreFeatures(FeatureSelectionInput input);

}
