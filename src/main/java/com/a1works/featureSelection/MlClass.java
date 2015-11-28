package com.a1works.featureSelection;

import com.a1works.commons.EqualsBuilder;
import com.a1works.commons.HashcodeBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public class MlClass implements Event {

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
    public boolean equals(Object other){
        for (EqualsBuilder<MlClass> equalsBuilder : EqualsBuilder.createInstanceIfParamsHaveSameType(this, other)) {
            MlClass otherMlClass = (MlClass)other;
            equalsBuilder.append(this.getName(), otherMlClass.getName());
            return equalsBuilder.isEqual();
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
        return String.format("MlClass(%s)", className);
    }
}
