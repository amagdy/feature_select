/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.a1works.featureSelect;

/**
 *
 * @author magdy
 */
public enum FeatureSelectionMetricName {
    PMI("pmi", PMI),
    CHI2("chi2");
    
    private String key = null;
    FeatureSelectionMetricName(String metric_name) {
        key = _key;
    }
    
    public static FeatureSelectionMetricName getMetricByKey(String _key) {
        for (FeatureSelectionMetricName f : FeatureSelectionMetricName.values()) {
            if (f.key.equals(_key)) return f;
        }
        throw new IllegalArgumentException("Invalid FeatureSelectionMetricName: " + _key);
    }
}
