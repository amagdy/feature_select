package com.a1works.featureSelection;

import com.a1works.commons.EqualsBuilder;
import com.a1works.commons.HashcodeBuilder;

/**
 * Created by Ahmed Magdy Ezzeldin <a.magdy@a1works.com> on 21/10/15.
 */
public final class ScoredFeature implements Event, Comparable<ScoredFeature> {

    private double score;
    private Feature feature;

    public ScoredFeature(Feature feature) {
        this.feature = feature;
    }

    public ScoredFeature(Feature feature, double score) {
        this(feature);
        this.score = score;
    }

    @Override
    public String getName(){
        return feature.getName();
    }

    public void setScore(double score){
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        for (EqualsBuilder<ScoredFeature> equalsBuilder : EqualsBuilder.createInstanceIfParamsHaveSameType(this, o)) {
            ScoredFeature otherFeature = equalsBuilder.getOtherObject();
            return equalsBuilder
                    .append(feature, otherFeature.feature)
                    .isEqual();
        }
        return false;
    }

    @Override
    public int hashCode(){
        return HashcodeBuilder.createInstance()
                .append(getName())
                .getHashCode();
    }

    @Override
    public int compareTo(ScoredFeature o) {
        if (o == null) return 1;
        return Double.compare(getScore(), o.getScore());
    }
}
