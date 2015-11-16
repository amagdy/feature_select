package com.a1works.utils;

import java.util.Arrays;

/**
 * Created by Ahmed Magdy Ezzeldin <a.magdy@a1works.com> on 4/11/15.
 */
public final class HashcodeBuilder {
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
        appendHashCode(intSuperHashCode);
        return this;
    }

    public HashcodeBuilder append(boolean boolField) {
        appendHashCode((boolField ? 1 : 0));
        return this;
    }

    public HashcodeBuilder append(byte byteField) {
        appendHashCode((int)byteField);
        return this;
    }

    public HashcodeBuilder append(char charField) {
        appendHashCode((int)charField);
        return this;
    }

    public HashcodeBuilder append(short shortField) {
        appendHashCode((int)shortField);
        return this;
    }
    public HashcodeBuilder append(int intField) {
        appendHashCode(intField);
        return this;
    }

    public HashcodeBuilder append(long longField) {
        appendHashCode((int) (longField ^ (longField >>> 32)));
        return this;
    }

    public HashcodeBuilder append(float floatField) {
        appendHashCode(Float.floatToIntBits(floatField));
        return this;
    }

    public HashcodeBuilder append(double doubleField) {
        long longVal = Double.doubleToLongBits(doubleField);
        appendHashCode((int) (longVal ^ (longVal >>> 32)));
        return this;
    }

    /**
     * Appends any object, object array or primitive array
     * @param objectField
     * @return HashcodeBuilder
     */
    public HashcodeBuilder append(Object objectField) {
        appendHashCode(getObjectHashCode(objectField));
        return this;
    }

    public int getHashCode(){return hashCode;}

    private void appendHashCode(int addedHashCode) {
        hashCode = (multiplier * hashCode) + addedHashCode;
    }

    private int getObjectHashCode(Object field) {
        if (field == null) return 0;
        Class<?> cls = field.getClass();
        if (cls.isArray()) {
            if (field instanceof boolean[]) {
                return Arrays.hashCode((boolean[])field);
            } else if (field instanceof byte[]) {
                return Arrays.hashCode((byte[]) field);
            } else if (field instanceof char[]) {
                return Arrays.hashCode((char[]) field);
            } else if (field instanceof short[]) {
                return Arrays.hashCode((short[]) field);
            } else if (field instanceof int[]) {
                return Arrays.hashCode((int[]) field);
            } else if (field instanceof long[]) {
                return Arrays.hashCode((long[]) field);
            } else if (field instanceof float[]) {
                return Arrays.hashCode((float[]) field);
            } else if (field instanceof double[]) {
                return Arrays.hashCode((double[]) field);
            } else {
                return Arrays.deepHashCode((Object[]) field);
            }
        } else {    // Object
            return field.hashCode();
        }
    }
}
