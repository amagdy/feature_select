package com.a1works.featureSelection;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public final class PmiAlgorithm implements FeatureSelectionAlgorithm {

    @Override
    public SortedMap<Feature, Score> scoreFeatures(FeatureSelectionInput input) {
        SortedMap<Feature, Score> result = new TreeMap<>();
        return result;
    }
}
