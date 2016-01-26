package com.a1works.featureSelection;

import com.a1works.featureSelection.MlClass;

import java.util.Set;

/**
 * Created by Ahmed_Magdy on 12/6/15.
 */
public interface Record {

    MlClass getMlClass();
    Set<Feature> getFeatures();
    long getFeatureFrequency(Feature feature);

}
