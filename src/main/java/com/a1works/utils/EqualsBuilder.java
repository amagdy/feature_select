package com.a1works.utils;

import java.util.Arrays;

/**
 * Created by Ahmed Magdy Ezzeldin <a.magdy@a1works.com> on 4/11/15.
 */
public class EqualsBuilder<T> {
    private Class<?> cls;
    private T target;
    private T otherObject;
    private boolean isEqual = false;

    private EqualsBuilder(){}

    /**
     * Creates an instance of the equalizer if and only if the 2 parameters are
     * not null and have the same type
     * @param target
     * @param other
     * @return MayBe<Equalizer<T>>
     */
    public static <T> MayBe<EqualsBuilder<T>> createInstanceIfParamsHaveSameType(T target, Object other){
        EqualsBuilder<T> instance = null;
        Class<?> cls;
        T otherObject;
        if (target != null) {
            cls = target.getClass();
            if (other != null && cls.equals(other.getClass())) {
                otherObject = (T)other;
                instance = new EqualsBuilder<T>();
                instance.target = target;
                instance.cls = cls;
                instance.otherObject = otherObject;
                instance.isEqual = true;
            }
        }
        return new MayBe<EqualsBuilder<T>>(instance);
    }

    public T getOtherObject() {
        return otherObject;
    }

    public EqualsBuilder appendSuper(boolean superEquals) {
        isEqual = isEqual && superEquals;
        return this;
    }

    public EqualsBuilder append(boolean field1, boolean field2) {
        isEqual = isEqual && (field1 == field2);
        return this;
    }

    public EqualsBuilder append(int field1, int field2) {
        isEqual = isEqual && (field1 == field2);
        return this;
    }

    public EqualsBuilder append(long field1, long field2) {
        isEqual = isEqual && (field1 == field2);
        return this;
    }

    public EqualsBuilder append(float field1, float field2) {
        isEqual = isEqual && (field1 == field2);
        return this;
    }

    public EqualsBuilder append(double field1, double field2) {
        isEqual = isEqual && (field1 == field2);
        return this;
    }


    public EqualsBuilder append(char field1, char field2) {
        isEqual = isEqual && (field1 == field2);
        return this;
    }



    public EqualsBuilder append(byte field1, byte field2) {
        isEqual = isEqual && (field1 == field2);
        return this;
    }

    public EqualsBuilder append(short field1, short field2) {
        isEqual = isEqual && (field1 == field2);
        return this;
    }


    public EqualsBuilder append(Object field1, Object field2) {
        isEqual = isEqual && areObjectsEqual(field1, field2);
        return this;
    }



    public boolean isEqual(){return isEqual;}

    private boolean areObjectsEqual(Object field1, Object field2) {
        if (field1 == null && field2 == null) return true;
        if(field1 == null) return false;
        if(field2 == null) return false;
        Class<?> cls = field1.getClass();
        if (cls != field2.getClass()) return false;
        if (cls.isArray()) {
            if (field1 instanceof boolean[]) {
                return Arrays.equals((boolean[])field1, (boolean[])field2);
            } else if (field1 instanceof byte[]) {
                return Arrays.equals((byte[])field1, (byte[])field2);
            } else if (field1 instanceof char[]) {
                return Arrays.equals((char[])field1, (char[])field2);
            } else if (field1 instanceof short[]) {
                return Arrays.equals((short[])field1, (short[])field2);
            } else if (field1 instanceof int[]) {
                return Arrays.equals((int[])field1, (int[])field2);
            } else if (field1 instanceof long[]) {
                return Arrays.equals((long[])field1, (long[])field2);
            } else if (field1 instanceof float[]) {
                return Arrays.equals((float[])field1, (float[])field2);
            } else if (field1 instanceof double[]) {
                return Arrays.equals((double[])field1, (double[])field2);
            } else {
                return Arrays.deepEquals((Object[])field1, (Object[])field2);
            }
        } else {    // Object
            return field1.equals(field2);
        }
    }



//    public EqualsBuilder append(boolean[] field1, boolean[] field2) {
//        isEqual = isEqual && Arrays.equals(field1, field2);
//        return this;
//    }
//    public EqualsBuilder append(int[] field1, int[] field2) {
//        isEqual = isEqual && Arrays.equals(field1, field2);
//        return this;
//    }
//    public EqualsBuilder append(long[] field1, long[] field2) {
//        isEqual = isEqual && Arrays.equals(field1, field2);
//        return this;
//    }
//    public EqualsBuilder append(float[] field1, float[] field2) {
//        isEqual = isEqual && Arrays.equals(field1, field2);
//        return this;
//    }
//    public EqualsBuilder append(double[] field1, double[] field2) {
//        isEqual = isEqual && Arrays.equals(field1, field2);
//        return this;
//    }
//
//    public EqualsBuilder append(char[] field1, char[] field2) {
//        isEqual = isEqual && Arrays.equals(field1, field2);
//        return this;
//    }
//    public EqualsBuilder append(byte[] field1, byte[] field2) {
//        isEqual = isEqual && Arrays.equals(field1, field2);
//        return this;
//    }
//    public EqualsBuilder append(short[] field1, short[] field2) {
//        isEqual = isEqual && Arrays.equals(field1, field2);
//        return this;
//    }
//    public EqualsBuilder append(Object[] field1, Object[] field2) {
//        isEqual = isEqual && Arrays.equals(field1, field2);
//        return this;
//    }
}
