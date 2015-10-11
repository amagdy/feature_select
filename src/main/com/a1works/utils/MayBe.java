package com.a1works.utils;

import org.junit.runner.notification.RunListener.ThreadSafe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


@ThreadSafe
public final class MayBe<OptionalType> implements Iterable<OptionalType> {

    private final List<OptionalType> list;

    public MayBe(OptionalType value){
        if (value != null) {
            List<OptionalType> tmpList = new ArrayList<OptionalType>(1);
            tmpList.add(value);
            list = Collections.unmodifiableList(tmpList);
        } else {
            list = Collections.emptyList();
        }
    }

    public MayBe(){
        this(null);
    }


    @Override
    public Iterator<OptionalType> iterator() {
        return list.iterator();
    }
}
