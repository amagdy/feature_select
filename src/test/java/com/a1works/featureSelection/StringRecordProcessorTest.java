package com.a1works.featureSelection;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringRecordProcessorTest {

    private List<Record> recordsIterableToList(Iterable<Record> records){
        List<Record> list = new ArrayList<>();
        for (Record record : records) {
            list.add(record);
        }
        return list;
    }

    @Test
    public void givenOneLineWhenParseThenItShouldWork(){
        MlClass cls = MlClass.getInstance("1");
        StringRecordProcessor stringProcessor = new StringRecordProcessor();
        List<Record> records = recordsIterableToList(stringProcessor.getRecords("1 1:1 2:1 3:1"));
        assertEquals("Number of records must be 1.", 1, records.size());
        Record record = records.get(0);
        assertEquals("MlClass must be the same the input", cls, record.getMlClass());
        assertEquals("Number of features must be like input", 3, record.getFeatures().size());
    }

    @Test
    public void given10LinesWhenParseThenItShouldWork(){
        MlClass cls = MlClass.getInstance("1");
        StringRecordProcessor stringProcessor = new StringRecordProcessor();
        List<Record> records = recordsIterableToList(stringProcessor.getRecords(
                "1 1:1 2:1 3:1 5:1\n" +
                "1 1:1 3:1 6:1\n" +
                "1 1:1 2:1 3:1 7:1\n" +
                "1 1:1 3:1 8:1\n" +
                "1 1:1 2:1 3:1 9:1\n" +
                "0 1:1 4:1 10:1\n" +
                "0 1:1 2:1 4:1 11:1\n" +
                "0 1:1 4:1 12:1\n" +
                "0 1:1 2:1 4:1 13:1\n" +
                "0 1:1 4:1 14:1"));
        assertEquals("Number of records must be equal to the number of lines.", 10, records.size());
    }

    @Test(expected = InvalidRecordFormatException.class)
    public void givenInvalidStringWhenParseThenThrowException(){
        StringRecordProcessor stringProcessor = new StringRecordProcessor();
        stringProcessor.getRecords("dsdsdsdsddsds");
    }

    @Test(expected = InvalidRecordFormatException.class)
    public void givenNullStringWhenParseThenThrowException(){
        StringRecordProcessor stringProcessor = new StringRecordProcessor();
        stringProcessor.getRecords(null);
    }

}
