package com.a1works.featureSelection;

import com.a1works.utils.EqualsBuilder;
import com.a1works.utils.HashcodeBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public class Feature implements Event {

    private final String featureName;

    private final static Map<String, Feature> INSTANCES = new HashMap<>();

    public static Feature getInstance(String featureName){
        Feature feature;
        synchronized (INSTANCES) {
            if (INSTANCES.containsKey(featureName)) {
                feature = INSTANCES.get(featureName);
            } else {
                feature = new Feature(featureName);
                INSTANCES.put(featureName, feature);
            }
        }
        return feature;
    }

    private Feature(String name) {
        this.featureName = name;
    }


    @Override
    public String getName(){
        return featureName;
    }


    @Override
    public boolean equals(Object o) {
        for (EqualsBuilder<Feature> equalsBuilder : EqualsBuilder.createInstanceIfParamsHaveSameType(this, o)) {
            Feature otherFeature = equalsBuilder.getOtherObject();
            return equalsBuilder
                    .append(getName(), otherFeature.getName())
                    .isEqual();
        }
        return false;
    }

    @Override
    public int hashCode(){
        return HashcodeBuilder.createInstance()
                .append(getName())
                .getHashCode();
    }

    @Override
    public String toString(){
        return String.format("Feature(%s)", featureName);
    }
}
