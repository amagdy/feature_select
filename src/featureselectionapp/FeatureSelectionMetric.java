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

        private double p_of_f(String _feature_name) {
            return (double) features_frequencies.get(_feature_name) / (double) all_data_set_records_count;
        }

        private double p_of_c(String _class_name) {
            return (double) classes_frequencies.get(_class_name) / (double) all_data_set_records_count;
        }

        private double p_of_f_intersect_c(String _class_name, String _feature_name) {
            Integer i = features_frequencies_per_class.get(_feature_name).get(_class_name);
            if (i != null) return (double)i / (double) all_data_set_records_count;
            return 0;
        }

        private double p_of_c_given_f(String _class_name, String _feature_name) {
            return p_of_f_intersect_c(_class_name, _feature_name) / p_of_f(_feature_name);
        }

        private double p_of_f_given_c(String _feature_name, String _class_name) {
            return p_of_f_intersect_c(_class_name, _feature_name) / p_of_c(_class_name);
        }

        private double log2(double _num) {
            return Math.log(_num) / Math.log(2);
        }

        private double pmiOfFeatureForAllClasses(String _feature_name) {
            // PMI(f) = sum<all_classes>(P(f,c) * Log2(P(f|c) / P(f)))
            double sum = 0.0;
            for (String class_name : classes_frequencies.keySet()) {
                // P(f, c)
                sum += p_of_f_intersect_c(class_name, _feature_name)
                        * log2(p_of_f_given_c(_feature_name, class_name) / p_of_f(_feature_name));
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

        private double classObservedFrequency(String _feature_name, String _class_name) {
            Integer i = features_frequencies_per_class.get(_feature_name).get(_class_name);
            if (i != null) return i;
            return 0;
        }

        private double classExpectedFrequency(String _class_name) {
            return (double) all_data_set_records_count / (double) all_classes_count;
        }

        private double chi2OfFeatureForAllClasses(String _feature_name) {
            /*
                X^2 = Sum (O-E)^2 / E
                O = C(f, c)
                E = all_records_count / class_count 
             */
            double sum = 0.0;
            for (String class_name : classes_frequencies.keySet()) {
                double O = classObservedFrequency(_feature_name, class_name);
                double E = classes_frequencies.get(class_name);//all_data_set_records_count / all_classes_count;
                sum += Math.pow(O - E, 2) / E;
            }
            return sum;
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
