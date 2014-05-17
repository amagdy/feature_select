/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureselectionapp;

import gnu.trove.map.TMap;

/**
 *
 * @author magdy
 */
public interface FeatureSelectionObserver {
    
    public void selectedFeatures(TMap<String, Double> _features_scores);
    
}
