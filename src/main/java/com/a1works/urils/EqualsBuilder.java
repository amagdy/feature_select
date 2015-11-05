package com.a1works.urils;

/**
 * Created by Ahmed Magdy Ezzeldin <a.magdy@a1works.com> on 4/11/15.
 */
public class EqualsBuilder<T> {
    private Class<?> cls;
    private T target;
    private T otherObject;
    private boolean isEquals = false;

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
                instance.isEquals = true;
            }
        }
        return new MayBe<EqualsBuilder<T>>(instance);
    }

    public T getOtherObject() {
        return otherObject;
    }

    public EqualsBuilder appendSuper(boolean superEquals) {
        hashCode += multiplier * superEquals;
        return this;
    }

    public EqualsBuilder append(boolean field, boolean field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public EqualsBuilder append(boolean[] field, boolean[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public EqualsBuilder append(int field, int field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public EqualsBuilder append(int[] field, int[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public EqualsBuilder append(long field, long field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public EqualsBuilder append(long[] field, long[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public EqualsBuilder append(float field, float field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public EqualsBuilder append(float[] field, float[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public EqualsBuilder append(double field, double field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public EqualsBuilder append(double[] field, double[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public EqualsBuilder append(char field, char field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public EqualsBuilder append(char[] field, char[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public EqualsBuilder append(byte field, byte field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public EqualsBuilder append(byte[] field, byte[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    public EqualsBuilder append(short field, short field2) {
        hashCode += multiplier * (field == field2);
        return this;
    }

    public EqualsBuilder append(short[] field, short[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * (field[i] == field2[i]);
        }
        return this;
    }

    private boolean areObjectsEqual(Object field, Object field2) {
        if (field == null && field2 == null) return true;
        if(field == null) return false;
        if(field2 == null) return false;
        return field.equals(field2);
    }

    public EqualsBuilder append(Object field, Object field2) {
        hashCode += multiplier * areObjectsEqual(field, field2);
        return this;
    }

    public EqualsBuilder append(Object[] field, Object[] field2) {
        hashCode += multiplier * (field == field2);
        if (isEquals) return this;
        hashCode += multiplier * (field.length == field2.length);
        for (int i = 0; isEquals && i < field.length; i++) {
            hashCode += multiplier * areObjectsEqual(field[i], field2[i]);
        }
        return this;
    }

    public boolean isEqual(){return isEquals;}

}
