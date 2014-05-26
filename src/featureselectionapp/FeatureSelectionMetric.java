/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureselectionapp;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author magdy
 */
public abstract class FeatureSelectionMetric {

    protected TMap<String, CustomStringIntHashMap> features_frequencies_per_class;
    protected CustomStringIntHashMap classes_frequencies;
    protected CustomStringIntHashMap features_frequencies;
    protected int all_classes_count;
    protected int all_features_count;
    protected int all_data_set_records_count;

    protected void takeInput(
            TMap<String, CustomStringIntHashMap> _features_frequencies_per_class,
            CustomStringIntHashMap _classes_frequencies,
            CustomStringIntHashMap _features_frequencies,
            int _all_data_set_records_count) {
        features_frequencies_per_class = _features_frequencies_per_class;
        classes_frequencies = _classes_frequencies;
        features_frequencies = _features_frequencies;
        all_classes_count = _classes_frequencies.size();
        all_features_count = _features_frequencies.size();
        all_data_set_records_count = _all_data_set_records_count;
    }

    public static FeatureSelectionMetric getInstance(
            FeatureSelectionMetricEnum _type,
            TMap<String, CustomStringIntHashMap> _features_frequencies_per_class,
            CustomStringIntHashMap _classes_frequencies,
            CustomStringIntHashMap _features_frequencies,
            int _all_data_set_records_count) {

        FeatureSelectionMetric metric = null;
        if (_type == FeatureSelectionMetricEnum.PMI) {
            metric = new MetricPMI();
        } else if (_type == FeatureSelectionMetricEnum.CHI2) {
            metric = new MetricChi2();
        } else {
            throw new IllegalArgumentException("Invalid FeatureSelectionMetric type");
        }
        metric.takeInput(
                _features_frequencies_per_class,
                _classes_frequencies,
                _features_frequencies,
                _all_data_set_records_count);
        return metric;
    }

    public abstract TMap<String, Double> execute();
    
    ///// List of Metrics ///////////////////////
    private static class MetricPMI extends FeatureSelectionMetric {

        private double p_of_f(String _feature) {
            return (double) features_frequencies.get(_feature) / (double) all_data_set_records_count;
        }

        private double p_of_c(String _class) {
            return (double) classes_frequencies.get(_class) / (double) all_data_set_records_count;
        }

        private double p_of_f_intersect_c(String _class, String _feature) {
		CustomStringIntHashMap map = features_frequencies_per_class.get(_feature);
		if (map == null) return 0;
		Integer i = map.get(_class);
		if (i == null) i = 0;
		return (double)i / (double) all_data_set_records_count;
        }

        private double p_of_c_given_f(String _class, String _feature) {
            return p_of_f_intersect_c(_class, _feature) / p_of_f(_feature);
        }

        private double p_of_f_given_c(String _feature, String _class) {
            return p_of_f_intersect_c(_class, _feature) / p_of_c(_class);
        }

        private double log2(double _num) {
            if (_num == 0.0) return 0.0;  
            return Math.log(_num) / Math.log(2);
        }

        private double pmiOfFeatureForAllClasses(String _feature) {
            // PMI(f) = sum<all_classes>(P(f,c) * Log2(P(f|c) / P(f)))
            double sum = 0.0;
            for (String class_name : classes_frequencies.keySet()) {
                // P(f, c)
                sum += p_of_f_intersect_c(class_name, _feature)
                        * log2(p_of_f_given_c(_feature, class_name) / p_of_f(_feature));
            }
            return sum;
        }

        @Override
        public TMap<String, Double> execute() {
            TMap<String, Double> scores = new THashMap(features_frequencies.size());
            for (String feature_name : features_frequencies.keySet()) {
                scores.put(feature_name, pmiOfFeatureForAllClasses(feature_name));
            }
            return scores;
        }
    }

    private static class MetricChi2 extends FeatureSelectionMetric {

	// class frequency
	private double Nc1(String _class){
		Integer i = classes_frequencies.get(_class);
		if (i != null) return (double)i.intValue();
		return 0;
	}
	
	// not class frequency
	private double Nc0(String _class, int _records_count){
		return _records_count - Nc1(_class);
	}
	
	// feature frequency
	private double Nf1(String _feature){
		Integer i = features_frequencies.get(_feature);
		if (i != null) return i;
		return 0;
	}
	
	// not feature frequency
	private double Nf0(String _feature, int _records_count){
		return _records_count - Nf1(_feature);
	}
	
	
        private double Nc1f1(String _class, String _feature) {
	        CustomStringIntHashMap map = features_frequencies_per_class.get(_feature);
	        if (map == null) return 0;
		Integer i = map.get(_class);
		if (i != null) return (double)i.intValue();
		return 0;
        }
        
        // class With Out Feature Frequency
        private double Nc1f0(String _class, String _feature) {
		return Nc1(_class) - Nc1f1(_class, _feature);
        }
        
        // feature With Out Class Frequency
	private double Nc0f1(String _class, String _feature) {
		return Nf1(_feature) - Nc1f1(_class, _feature);
        }
        
        // frequency With out Class And Feature
        private double Nc0f0(String _class, String _feature, int _records_count) {
		return _records_count - ((Nc1(_class) + Nf1(_feature)) - Nc1f1(_class, _feature));
        }        

        private double chi2OfFeatureForAllClasses(String _feature) {
		double sum = 0.0;
		double nc1f1, nc0f0, nc1f0, nc0f1, nc1, nf1, nf0, nc0;
		int n = all_data_set_records_count;
		nf1 = Nf1(_feature);
		nf0 = Nf0(_feature, n);
		for (String _class : classes_frequencies.keySet()) {
			nc1   = Nc1(_class);
			nc0   = Nc0(_class, n);
			nc1f1 = Nc1f1(_class, _feature);
			nc0f0 = Nc0f0(_class, _feature, n);
			nc1f0 = Nc1f0(_class, _feature);
			nc0f1 = Nc0f1(_class, _feature);
			//http://blog.datumbox.com/using-feature-selection-methods-in-text-classification/
			double val = (n * Math.pow((nc1f1 * nc0f0) - (nc1f0 * nc0f1), 2.0)) / (
		    	(nc1f1 + nc0f1) *
		    	(nc1f1 + nc1f0) *
		    	(nc1f0 + nc0f0) *
		    	(nc0f1 + nc0f0)
		    );
		    	if (val > sum) sum = val; 
		}
            return sum;// / (double)all_classes_count;
        }

        @Override
        public TMap<String, Double> execute() {
            TMap<String, Double> scores = new THashMap(features_frequencies.size());
            for (String feature_name : features_frequencies.keySet()) {
                scores.put(feature_name, chi2OfFeatureForAllClasses(feature_name));
            }
            return scores;
        }
    }
}
