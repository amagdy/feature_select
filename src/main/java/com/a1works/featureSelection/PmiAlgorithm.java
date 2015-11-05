package com.a1works.featureSelection;

import java.util.*;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public final class PmiAlgorithm implements FeatureSelectionAlgorithm {

    private FeatureSelectionInput input;


    private double log2(double _num) {
        if (_num == 0.0) return 0.0;
        return Math.log(_num) / Math.log(2);
    }

    private ScoredFeature pmiOfFeatureForAllClasses(Feature feature) {
        // PMI(f) = sum<all_classes>(P(f,c) * Log2(P(f|c) / P(f)))
        ProbabilityCalculator prob = ProbabilityCalculator.createInstance(input);
        double sum = 0.0;
        for (MlClass cls : input.getMlClasses()) {
            sum +=
                    prob.getProbabilityOfFeatureAndMlClass(feature, cls)            // P(f, c)
                    * log2(
                            prob.getProbabilityOfFeatureGivenMlClass(feature, cls)  // P(f | c)
                            /
                            prob.getProbabilityOfFeature(feature)                   // P(f)
                    );
        }
        return new ScoredFeature(feature, sum);
    }

    @Override
    public SortedSet<ScoredFeature> scoreFeatures(final FeatureSelectionInput input) {
        this.input = input;
        SortedSet<ScoredFeature> scores = new TreeSet<ScoredFeature>();
        for (Feature feature: input.getFeatures()) {
            scores.add(pmiOfFeatureForAllClasses(feature));
        }
        return scores;
    }
}
