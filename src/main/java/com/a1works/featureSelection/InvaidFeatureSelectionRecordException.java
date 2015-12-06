package com.a1works.featureSelection;

/**
 * Created by Ahmed_Magdy on 12/6/15.
 */
public class InvaidFeatureSelectionRecordException extends Exception {

    public InvaidFeatureSelectionRecordException(String record, String message){
        super(String.format("%s : \"%s\"", message, record));
    }

    public InvaidFeatureSelectionRecordException(String record) {
        this(record, "Invalid Record format");
    }

}
