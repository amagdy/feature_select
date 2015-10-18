package com.a1works.featureSelection;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public class Score implements Comparable<Score> {

    private final double score;

    public Score(double score) {
        this.score = score;
    }

    public double doubleValue() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Score))
            return false;
        return ((Score)o).doubleValue() == doubleValue();
    }

    @Override
    public int compareTo(Score o) {
        if (o == null) return 1;
        return Double.compare(doubleValue(), o.doubleValue());
    }
}
