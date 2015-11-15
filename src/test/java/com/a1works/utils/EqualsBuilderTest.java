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
        obj1 = createTestObject1();
        obj2 = createTestObject2();
    }

    @Test
    public void testEqualsOnEqualObjects(){
        TestEntityObject obj1 = createTestObject1();
        TestEntityObject obj2 = createTestObject1();
        assertTrue("The 2 objects are expected to be equal", areObjectsEqualAllFields(obj1, obj2));
    }

    @Test
    public void testEqualsOneValueIsIsNull(){
        TestEntityObject obj = createTestObject1();
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



    private TestEntityObject createTestObject(boolean boolValue,
                                              boolean[] boolArrayValue,
                                              int intValue,
                                              int[] intArrayValue,
                                              short shortValue,
                                              short[] shortArrayValue,
                                              long longValue,
                                              long[] longArrayValue,
                                              char charValue,
                                              char[] charArrayValue,
                                              byte byteValue,
                                              byte[] byteArrayValue,
                                              float floatValue,
                                              float[] floatArrayValue,
                                              double doubleValue,
                                              double[] doubleArrayValue,
                                              String stringValue,
                                              String[] stringArrayValue){
        TestEntityObject target = new TestEntityObject();
        target.boolValue = boolValue;
        target.boolArrayValue = boolArrayValue;

        target.intValue = intValue;
        target.intArrayValue = intArrayValue;

        target.shortValue = shortValue;
        target.shortArrayValue = shortArrayValue;

        target.longValue = longValue;
        target.longArrayValue = longArrayValue;

        target.charValue = charValue;
        target.charArrayValue = charArrayValue;

        target.byteValue = byteValue;
        target.byteArrayValue = byteArrayValue;

        target.floatValue = floatValue;
        target.floatArrayValue = floatArrayValue;

        target.doubleValue = doubleValue;
        target.doubleArrayValue = doubleArrayValue;

        target.stringValue = stringValue;
        target.stringArrayValue = stringArrayValue;

        return target;
    }
    private TestEntityObject createTestObject1(){
        TestEntityObject obj = createTestObject(
                true,
                new boolean[]{true, false, true},
                1,
                new int[]{1, 2, 3},
                (short)2,
                new short[]{4, 5, 6},
                (long)3,
                new long[]{7, 8, 9},
                'a',
                new char[]{'a', 'b', 'c'},
                (byte)1,
                new byte[]{1, 2, 3},
                (float)10.0,
                new float[]{(float)11.0, (float)12.0, (float)13.0},
                14.0,
                new double[]{15.0, 16.0, 17.0},
                "Name1",
                new String[]{"name1", "first1", "last1"}
        );
        return obj;
    }

    private TestEntityObject createTestObject2(){
        TestEntityObject obj = createTestObject(
                false,
                new boolean[]{false, false, false},
                11,
                new int[]{11, 12, 13},
                (short)12,
                new short[]{14, 15, 16},
                (long)13,
                new long[]{17, 18, 19},
                'b',
                new char[]{'e', 'f', 'g'},
                (byte)11,
                new byte[]{11, 12, 13},
                (float)110.0,
                new float[]{(float)111.0, (float)112.0, (float)113.0},
                114.0,
                new double[]{115.0, 116.0, 117.0},
                "Name2",
                new String[]{"name2", "first2", "last2"}
        );
        return obj;
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


