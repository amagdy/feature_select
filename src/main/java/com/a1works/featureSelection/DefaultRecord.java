package com.a1works.featureSelection;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public final class DefaultRecord implements Record {
    private MlClass cls;
    private Map<Feature, Long> featuresFrequencies;

    public DefaultRecord(MlClass _cls, Map<Feature, Long> _featuresFrequencies){
        cls = _cls;
        featuresFrequencies = Collections.unmodifiableMap(_featuresFrequencies);
    }

    @Override
    public MlClass getMlClass() {
        return cls;
    }

    @Override
    public Set<Feature> getFeatures() {
        return Collections.unmodifiableSet(featuresFrequencies.keySet());
    }

    @Override
    public long getFeatureFrequency(Feature feature) {
        return featuresFrequencies.get(feature).longValue();
    }
}
