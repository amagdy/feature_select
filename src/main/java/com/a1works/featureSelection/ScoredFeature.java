package com.a1works.featureSelection;

/**
 * Created by Ahmed Magdy Ezzeldin <a.magdy@a1works.com> on 21/10/15.
 */
public class ScoredFeature implements Event, Comparable<ScoredFeature> {

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
        if (this == o) return true;
        if (!(o instanceof ScoredFeature))
            return false;
        return ((ScoredFeature)o).getScore() == getScore();
    }

    @Override
    public int compareTo(ScoredFeature o) {
        if (o == null) return 1;
        return Double.compare(getScore(), o.getScore());
    }
}
