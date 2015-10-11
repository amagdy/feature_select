package com.a1works.featureSelect;

/**
 * Created by magdy on 11.10.15.
 */
public interface AlgorithmFactory {

    public FeatureSelectionAlgorithm getAlgorithmByName(String algorithm_name);

    public boolean isAlgorithmNameValid(String algorithm_name);

    public String[] getAvailableAlgorithmNames();

}
