package com.a1works.featureSelection;

import java.util.SortedSet;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public interface Algorithm {

    public SortedSet<ScoredFeature> scoreFeatures(Input input);

}
