package com.a1works.featureSelection;

import com.a1works.featureSelection.Feature;
import com.a1works.featureSelection.MlClass;

import java.util.Set;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public interface FeatureSelectionInput {

    public Set<Feature> getFeatures();

    public Set<MlClass> getMlClasses();

    public long getFeatureFrequency(Feature feature);

    public long getMlClassFrequency(MlClass cls);

    public long getFeatureFrequencyPerClass(Feature feature, MlClass cls);

    public long getRecordsCount();

    public Set<Feature> getMlClassFeatures(MlClass cls);

}
