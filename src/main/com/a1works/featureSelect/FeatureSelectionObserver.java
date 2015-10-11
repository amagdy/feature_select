/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.a1works.featureSelect;


import java.util.Map;

/**
 *
 * @author magdy
 */
public interface FeatureSelectionObserver {
    
    public void selectedFeatures(Map<String, Double> _features_scores);
    
}
