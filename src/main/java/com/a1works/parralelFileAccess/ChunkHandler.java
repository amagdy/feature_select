package com.a1works.parralelFileAccess;

import com.a1works.featureSelection.Input;
import com.a1works.featureSelection.Record;

/**
 * Created by magdy on 02.02.16.
 */
public interface ChunkHandler<E> {
    void processLine(String line);
    E finished();
    void error(Exception ex);
}
