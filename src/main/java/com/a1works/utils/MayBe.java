package com.a1works.utils;

import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Ahmed Magdy Ezzeldin <a.magdy@a1works.com> on 4/11/15.
 */
public class MayBe<T> implements Iterable<T> {
    private Iterator<T> iterator;


    public MayBe(T wrappedObject) {
        if (wrappedObject == null) {
            this.iterator = Collections.emptyIterator();
        } else {
            this.iterator = Collections.singletonList(wrappedObject).iterator();
        }
    }

    public MayBe() {
        this(null);
    }

    public boolean isEmpty(){
        return !iterator.hasNext();
    }

    public T get(){
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }


    @Override
    public Iterator<T> iterator() {
        return iterator;
    }
}
