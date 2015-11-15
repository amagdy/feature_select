package com.a1works.utils;

import java.util.Arrays;

/**
 * Created by Ahmed Magdy Ezzeldin <a.magdy@a1works.com> on 4/11/15.
 */
public class HashcodeBuilder {
    private static final int DEFAULT_INITIAL_PRIME = 31;
    private static final int DEFAULT_MULTIPLIER_PRIME = 13;

    private int multiplier;
    private int hashCode;

    private HashcodeBuilder(){}


    public static HashcodeBuilder createInstance(int initialPrime, int multiplierPrime){
        HashcodeBuilder instance = new HashcodeBuilder();
        instance.hashCode = initialPrime;
        instance.multiplier = multiplierPrime;
        return instance;
    }

    public static HashcodeBuilder createInstance(){
        return createInstance(DEFAULT_INITIAL_PRIME, DEFAULT_MULTIPLIER_PRIME);
    }

    public HashcodeBuilder appendSuper(int intSuperHashCode) {
        hashCode = (multiplier * hashCode) + intSuperHashCode;
        return this;
    }

    public HashcodeBuilder append(boolean boolField) {
        hashCode = (multiplier * hashCode) + (boolField ? 1 : 0);
        return this;
    }

    public HashcodeBuilder append(boolean[] arrayField) {
        hashCode = (multiplier * hashCode) + (arrayField == null ? 0 : Arrays.hashCode(arrayField));
        return this;
    }

    public HashcodeBuilder append(int intField) {
        hashCode = (multiplier * hashCode) + intField;
        return this;
    }

    public HashcodeBuilder append(int[] arrayField) {
        hashCode = (multiplier * hashCode) + (arrayField == null ? 0 : Arrays.hashCode(arrayField));
        return this;
    }

    public HashcodeBuilder append(long longField) {
        hashCode = (multiplier * hashCode) + (int) (longField ^ (longField >>> 32));
        return this;
    }

    public HashcodeBuilder append(long[] arrayField) {
        hashCode = (multiplier * hashCode) + (arrayField == null ? 0 : Arrays.hashCode(arrayField));
        return this;
    }

    public HashcodeBuilder append(float floatField) {
        hashCode = (multiplier * hashCode) + Float.floatToIntBits(floatField);;
        return this;
    }

    public HashcodeBuilder append(float[] arrayField) {
        hashCode = (multiplier * hashCode) + (arrayField == null ? 0 : Arrays.hashCode(arrayField));
        return this;
    }

    public HashcodeBuilder append(double doubleField) {
        long longVal = Double.doubleToLongBits(doubleField);
        hashCode = (multiplier * hashCode) + (int) (longVal ^ (longVal >>> 32));
        return this;
    }

    public HashcodeBuilder append(double[] arrayField) {
        hashCode = (multiplier * hashCode) + (arrayField == null ? 0 : Arrays.hashCode(arrayField));
        return this;
    }

    public HashcodeBuilder append(char charField) {
        hashCode = (multiplier * hashCode) + (int)charField;
        return this;
    }

    public HashcodeBuilder append(char[] arrayField) {
        hashCode = (multiplier * hashCode) + (arrayField == null ? 0 : Arrays.hashCode(arrayField));
        return this;
    }

    public HashcodeBuilder append(byte byteField) {
        hashCode = (multiplier * hashCode) + (int)byteField;
        return this;
    }

    public HashcodeBuilder append(byte[] arrayField) {
        hashCode = (multiplier * hashCode) + (arrayField == null ? 0 : Arrays.hashCode(arrayField));
        return this;
    }

    public HashcodeBuilder append(short shortField) {
        hashCode = (multiplier * hashCode) + (int)shortField;
        return this;
    }

    public HashcodeBuilder append(short[] arrayField) {
        hashCode = (multiplier * hashCode) + (arrayField == null ? 0 : Arrays.hashCode(arrayField));
        return this;
    }

    public HashcodeBuilder append(Object objectField) {
        hashCode = (multiplier * hashCode) + objectField.hashCode();
        return this;
    }

    public HashcodeBuilder append(Object[] arrayField) {
        hashCode = (multiplier * hashCode) + (arrayField == null ? 0 : Arrays.hashCode(arrayField));
        return this;
    }

    public int getHashCode(){return hashCode;}

}
