package com.a1works.featureSelect;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

/**
 * Created by magdy on 11.10.15.
 */
public class FeatureSelectionAlgorithmFactory implements AlgorithmFactory {

    private static final AlgorithmFactory INSTANCE = new FeatureSelectionAlgorithmFactory();


    public static AlgorithmFactory getInstance(){
        return INSTANCE;
    }

    private FeatureSelectionAlgorithmFactory(){}


    @Override
    public FeatureSelectionAlgorithm getAlgorithmByName(String algorithm_name) {
        return null;
    }

    @Override
    public boolean isAlgorithmNameValid(String algorithm_name) {
        return false;
    }

    @Override
    public String[] getAvailableAlgorithmNames() {
        return new String[0];
    }




}
