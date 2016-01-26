package com.a1works.featureSelection;


import java.util.SortedSet;
import java.util.TreeSet;

public final class PmiAlgorithm implements Algorithm {

    private Input input;


    private double log2(double _num) {
        if (_num == 0.0) return 0.0;
        return Math.log(_num) / Math.log(2);
    }


    private double getProbability(long frequency) {
        return (double)frequency / (double)input.getRecordsCount();
    }

    private double getProbabilityOfFeature(Feature feature){
        return getProbability(input.getFeatureFrequency(feature));
    }

    private double getProbabilityOfMlClass(MlClass cls){
        return getProbability(input.getMlClassFrequency(cls));
    }

    private double getProbabilityOfFeatureAndMlClass(Feature feature, MlClass cls){
        return getProbability(input.getFeatureFrequencyPerClass(feature, cls));
    }

    private double getProbabilityOfMlClassGivenFeature(MlClass cls, Feature feature) {
        return getProbabilityOfFeatureAndMlClass(feature, cls) / getProbabilityOfFeature(feature);
    }

    private double getProbabilityOfFeatureGivenMlClass(Feature feature, MlClass cls) {
        return getProbabilityOfFeatureAndMlClass(feature, cls) / getProbabilityOfMlClass(cls);
    }

    private ScoredFeature pmiOfFeatureForAllClasses(Feature feature) {
        // PMI(f) = sum<all_classes>(P(f,c) * Log2(P(f|c) / P(f)))
        double sum = 0.0;
        for (MlClass cls : input.getMlClasses()) {
            sum +=
                    getProbabilityOfFeatureAndMlClass(feature, cls)            // P(f, c)
                    * log2(
                            getProbabilityOfFeatureGivenMlClass(feature, cls)  // P(f | c)
                            /
                            getProbabilityOfFeature(feature)                   // P(f)
                    );
        }
        return new ScoredFeature(feature, sum);
    }

    @Override
    public SortedSet<ScoredFeature> scoreFeatures(final Input input) {
        this.input = input;
        SortedSet<ScoredFeature> scores = new TreeSet<>();
        for (Feature feature: input.getFeatures()) {
            scores.add(pmiOfFeatureForAllClasses(feature));
        }
        return scores;
    }
}
