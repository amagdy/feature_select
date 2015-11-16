package com.a1works.utils;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
* Created by Ahmed Magdy Ezzeldin <a.magdy@a1works.com> on 15/11/15.
*/
public class EqualsBuilderTest {


    private final String NOT_EQUAL_MSG = "The 2 objects are expected NOT to be equal : ";
    private TestEntityObject obj1;
    private TestEntityObject obj2;

    @Before
    public void setup(){
        obj1 = TestEntityObject.createInstanceWithData1();
        obj2 = TestEntityObject.createInstanceWithData2();
    }

    @Test
    public void testEqualsOnEqualObjects(){
        TestEntityObject obj1 = TestEntityObject.createInstanceWithData1();
        TestEntityObject obj2 = TestEntityObject.createInstanceWithData1();
        assertTrue("The 2 objects are expected to be equal", areObjectsEqualAllFields(obj1, obj2));
    }

    @Test
    public void testEqualsOneValueIsIsNull(){
        TestEntityObject obj = TestEntityObject.createInstanceWithData1();
        assertFalse(NOT_EQUAL_MSG  + "because the first object is null", areObjectsEqualAllFields(null, obj));
        assertFalse(NOT_EQUAL_MSG  + "because the second object is null", areObjectsEqualAllFields(obj, null));
    }

    @Test
    public void testUnequalObjectsOnBooleanField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(boolean, boolean)",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{boolean.class}));
    }

    @Test
    public void testUnequalObjectsOnBooleanArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(boolean[], boolean[])",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{boolean[].class}));
    }

    @Test
    public void testUnequalObjectsOnIntField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(int, int)",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{int.class}));
    }

    @Test
    public void testUnequalObjectsOnIntArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(int[], int[])",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{int[].class}));
    }

    @Test
    public void testUnequalObjectsOnShortField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(short, short)",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{short.class}));
    }

    @Test
    public void testUnequalObjectsOnShortArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(short[], short[])",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{short[].class}));
    }

    @Test
    public void testUnequalObjectsOnLongField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(long, long)",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{long.class}));
    }

    @Test
    public void testUnequalObjectsOnLongArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(long[], long[])",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{long[].class}));
    }

    @Test
    public void testUnequalObjectsOnCharField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(char, char)",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{char.class}));
    }

    @Test
    public void testUnequalObjectsOnCharArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(char[], char[])",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{char[].class}));
    }

    @Test
    public void testUnequalObjectsOnByteField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(byte, byte)",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{byte.class}));
    }

    @Test
    public void testUnequalObjectsOnByteArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(byte[], byte[])",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{byte[].class}));
    }

    @Test
    public void testUnequalObjectsOnFloatField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(float, float)",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{float.class}));
    }

    @Test
    public void testUnequalObjectsOnFloatArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(float[], float[])",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{float[].class}));
    }

    @Test
    public void testUnequalObjectsOnDoubleField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(double, double)",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{double.class}));
    }

    @Test
    public void testUnequalObjectsOnDoubleArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(double[], double[])",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{double[].class}));
    }

    @Test
    public void testUnequalObjectsOnObjectField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(Object, Object)",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{String.class}));
    }

    @Test
    public void testUnequalObjectsOnObjectArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(Object[], Object[])",
                areObjectsEqual(obj1, obj2, true, new Class<?>[]{String[].class}));

    }


    private <T> boolean areObjectsEqual(T obj1, T obj2, boolean includeSuper, Class<?>[] classes){
        MayBe<EqualsBuilder<T>> mayBeBuilder = EqualsBuilder.createInstanceIfParamsHaveSameType(obj1, obj2);
        if (mayBeBuilder.isEmpty()) return false;
        EqualsBuilder<T> builder = mayBeBuilder.get();
        ////FIXME I am checking the classes instead of checking the super equals because I do not have access to the super.equals()
        if (includeSuper) builder.appendSuper(obj1.getClass().equals(obj2.getClass()));
        for (Class<?> cls : classes) {
            Field[] fields = obj1.getClass().getFields();
            for (Field field : fields) {
                if (field.getType().equals(cls)) {
                    try {
                        if (cls.isPrimitive()) {
                            if (cls.equals(boolean.class)) {
                                builder.append(field.getBoolean(obj1), field.getBoolean(obj2));
                            } else if (cls.equals(byte.class)) {
                                builder.append(field.getByte(obj1), field.getByte(obj2));
                            } else if (cls.equals(char.class)) {
                                builder.append(field.getChar(obj1), field.getChar(obj2));
                            } else if (cls.equals(short.class)) {
                                builder.append(field.getShort(obj1), field.getShort(obj2));
                            } else if (cls.equals(int.class)) {
                                builder.append(field.getInt(obj1), field.getInt(obj2));
                            } else if (cls.equals(long.class)) {
                                builder.append(field.getLong(obj1), field.getLong(obj2));
                            } else if (cls.equals(float.class)) {
                                builder.append(field.getFloat(obj1), field.getFloat(obj2));
                            } else if (cls.equals(double.class)) {
                                builder.append(field.getDouble(obj1), field.getDouble(obj2));
                            }
                        } else {
                            builder.append(field.get(obj1), field.get(obj2));
                        }
                    } catch (IllegalAccessException e) {
                        continue;
                    }
                }
            }
        }
        return builder.isEqual();
    }

    private boolean areObjectsEqualAllFields(TestEntityObject obj1, TestEntityObject obj2) {
        return areObjectsEqual(obj1, obj2, true, new Class<?>[]{
                boolean.class, boolean[].class,
                byte.class, byte[].class,
                char.class, char[].class,
                short.class, short[].class,
                int.class, int[].class,
                long.class, long[].class,
                float.class, float[].class,
                double.class, double[].class,
                String.class, String[].class});
    }
}


