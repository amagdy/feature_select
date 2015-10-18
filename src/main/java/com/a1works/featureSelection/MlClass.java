package com.a1works.featureSelection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public class MlClass implements NamedObject {

    private final String className;

    private final static Map<String, MlClass> INSTANCES = new HashMap<>();

    public static MlClass getInstance(String className){
        MlClass cls;
        synchronized (INSTANCES) {
            if (INSTANCES.containsKey(className)) {
                cls = INSTANCES.get(className);
            } else {
                cls = new MlClass(className);
                INSTANCES.put(className, cls);
            }
        }
        return cls;
    }

    private MlClass(String className){
        this.className = className;
    }

    @Override
    public String getName(){
        return className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MlClass))
            return false;
        return ((MlClass)o).getName().equals(getName());
    }

    @Override
    public String toString(){
        return String.format("MlClass(%s)", className);
    }
}
