package com.a1works.featureSelection;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public class Feature implements NamedObject{

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
        if (this == o) return true;
        if (!(o instanceof Feature))
            return false;
        return ((Feature)o).getName().equals(getName());
    }

    @Override
    public String toString(){
        return String.format("Feature(%s)", featureName);
    }
}
