/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureselectionapp;

/**
 *
 * @author magdy
 */
public enum FeatureSelectionMetricEnum {
    PMI("pmi"),
    CHI2("chi2");
    
    private String key = null;
    FeatureSelectionMetricEnum(String _key) {
        key = _key;
    }
    
    public static FeatureSelectionMetricEnum getMetricByKey(String _key) {
        for (FeatureSelectionMetricEnum f : FeatureSelectionMetricEnum.values()) {
            if (f.key.equals(_key)) return f;
        }
        throw new IllegalArgumentException("Invalid FeatureSelectionMetricEnum: " + _key);
    }
}
