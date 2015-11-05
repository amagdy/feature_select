package com.a1works.featureSelection;

/**
 * Created by Ahmed Magdy Ezzeldin <a.magdy@a1works.com> on 21/10/15.
 */
public final class ProbabilityCalculator {

    private FeatureSelectionInput input;

    public static ProbabilityCalculator createInstance(FeatureSelectionInput input){
        ProbabilityCalculator instance = new ProbabilityCalculator();
        instance.input = input;
        return instance;
    }

    private ProbabilityCalculator(){}

    private double getProbability(long frequency) {
        return (double)frequency / (double)input.getRecordsCount();
    }

    public double getProbabilityOfFeature(Feature feature){
        return getProbability(input.getFeatureFrequency(feature));
    }

    public double getProbabilityOfMlClass(MlClass cls){
        return getProbability(input.getMlClassFrequency(cls));
    }

    public double getProbabilityOfFeatureAndMlClass(Feature feature, MlClass cls){
        return getProbability(input.getFeatureFrequencyPerClass(feature, cls));
    }

    public double getProbabilityOfMlClassGivenFeature(MlClass cls, Feature feature) {
        return getProbabilityOfFeatureAndMlClass(feature, cls) / getProbabilityOfFeature(feature);
    }

    public double getProbabilityOfFeatureGivenMlClass(Feature feature, MlClass cls) {
        return getProbabilityOfFeatureAndMlClass(feature, cls) / getProbabilityOfMlClass(cls);
    }

}
