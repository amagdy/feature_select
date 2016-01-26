package com.a1works.featureSelection;

/**
 * Created by Ahmed_Magdy on 12/6/15.
 */
public interface RecordProcessor<T> {

    Iterable<Record> getRecords(T input) throws InvalidRecordFormatException;

}
