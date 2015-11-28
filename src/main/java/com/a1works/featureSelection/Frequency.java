package com.a1works.featureSelection;

import com.a1works.commons.EqualsBuilder;
import com.a1works.commons.HashcodeBuilder;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Ahmed Magdy Ezzeldin <a.magdy@a1works.com> on 25/10/15.
 */
final class Frequency<E extends Event> {
    private E event;
    private AtomicLong frequency;

    public Frequency(E event){
        this.event = event;
        frequency = new AtomicLong(0);
    }

    public Frequency(E event, long frequency){
        this.event = event;
        this.frequency = new AtomicLong(frequency);
    }

    public long getFrequency(){
        return frequency.longValue();
    }

    public void incrementFrequency(long incrementValue){
        frequency.addAndGet(incrementValue);
    }

    public E getEvent(){
        return event;
    }

    @Override
    public boolean equals(Object other){
        for (EqualsBuilder<Frequency<E>> equalsBuilder : EqualsBuilder.createInstanceIfParamsHaveSameType(this, other)) {
            Frequency<E> otherEventFrequency = (Frequency<E>)other;
            equalsBuilder.append(this.getEvent(), otherEventFrequency.getEvent());
            return equalsBuilder.isEqual();
        }
        return false;
    }

    @Override
    public int hashCode(){
        return HashcodeBuilder.createInstance()
                .append(getEvent())
                .getHashCode();
    }

}
