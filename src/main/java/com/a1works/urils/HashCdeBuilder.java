package com.a1works.urils;

/**
 * Created by Ahmed Magdy Ezzeldin <a.magdy@a1works.com> on 4/11/15.
 */
public class HashCdeBuilder {
    private static final int DEFAULT_INITIAL_PRIME = 31;
    private static final int DEFAULT_MULTIPLIER_PRIME = 13;

    private int multiplier;
    private int hashCode;

    private HashCdeBuilder(){}


    public static HashCdeBuilder createInstance(int initialPrime, int multiplierPrime){
        HashCdeBuilder instance = new HashCdeBuilder();
        instance.hashCode = initialPrime;
        instance.multiplier = multiplierPrime;
        return instance;
    }

    public static HashCdeBuilder createInstance(){
        return createInstance(DEFAULT_INITIAL_PRIME, DEFAULT_MULTIPLIER_PRIME);
    }

    public HashCdeBuilder appendSuper(int superHashCode) {
        hashCode += multiplier * superHashCode;
        return this;
    }

    public HashCdeBuilder append(boolean field) {
        hashCode += (field ? multiplier : 0);
        return this;
    }

    public HashCdeBuilder append(boolean[] field) {
        if (field == null) return this;
        for (int i = 0; i < field.length; i++) {
            hashCode += (field[i] ? multiplier : 0);
        }
        return this;
    }

    public HashCdeBuilder append(int field) {
        hashCode += multiplier * field;
        return this;
    }

    public HashCdeBuilder append(int[] field, int[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public HashCdeBuilder append(long field, long field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public HashCdeBuilder append(long[] field, long[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public HashCdeBuilder append(float field, float field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public HashCdeBuilder append(float[] field, float[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public HashCdeBuilder append(double field, double field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public HashCdeBuilder append(double[] field, double[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public HashCdeBuilder append(char field, char field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public HashCdeBuilder append(char[] field, char[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public HashCdeBuilder append(byte field, byte field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public HashCdeBuilder append(byte[] field, byte[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public HashCdeBuilder append(short field, short field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public HashCdeBuilder append(short[] field, short[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public HashCdeBuilder append(Object field) {
        hashCode += multiplier * (object == null ? 0 : object.hashCode());
        return this;
    }

    public HashCdeBuilder append(Object[] field, Object[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * areObjectsEqual(field[i], field2[i]);
        }
        return this;
    }

    public int getHashCode(){return hashCode;}

}
