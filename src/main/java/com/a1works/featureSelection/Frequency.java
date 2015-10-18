package com.a1works.featureSelection;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public class Frequency implements Comparable<Frequency> {

    private final AtomicLong frequency = new AtomicLong();

    public Frequency(){

    }

    public Frequency(long initialFrequency) {
        frequency.set(initialFrequency);
    }

    public long incrementBy(long inc) {
        return frequency.addAndGet(inc);
    }

    public long longValue(){
        return frequency.get();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Frequency))
            return false;
        return ((Frequency)o).longValue() == longValue();
    }

    @Override
    public String toString(){
        return String.format("Frequency(%ld)", longValue());
    }

    @Override
    public int compareTo(Frequency o) {
        if (o == null) return 1;
        return Long.compare(longValue(), o.longValue());
    }
}
