package com.a1works.utils;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ahmed Magdy Ezzeldin <a.magdy@a1works.com> on 15/11/15.
 */
public class HashcodeBuilderTest {


    private final String NOT_EQUAL_MSG = "The hash code of the 2 objects is expected to be different : ";
    private TestEntityObject obj1;
    private TestEntityObject obj2;

    @Before
    public void setup(){
        obj1 = TestEntityObject.createInstanceWithData1();
        obj2 = TestEntityObject.createInstanceWithData2();
    }

    @Test
    public void testHashCodeOnEqualObjects(){
        TestEntityObject obj1 = TestEntityObject.createInstanceWithData1();
        TestEntityObject obj2 = TestEntityObject.createInstanceWithData1();
        assertTrue("The 2 objects are expected to have the same hash code", areHashCodesEqualAllFields(obj1, obj2));
    }

    @Test
    public void testUnequalObjectsOnBooleanField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(boolean, boolean)",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{boolean.class}));
    }

    @Test
    public void testUnequalObjectsOnBooleanArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(boolean[], boolean[])",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{boolean[].class}));
    }

    @Test
    public void testUnequalObjectsOnIntField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(int, int)",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{int.class}));
    }

    @Test
    public void testUnequalObjectsOnIntArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(int[], int[])",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{int[].class}));
    }

    @Test
    public void testUnequalObjectsOnShortField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(short, short)",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{short.class}));
    }

    @Test
    public void testUnequalObjectsOnShortArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(short[], short[])",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{short[].class}));
    }

    @Test
    public void testUnequalObjectsOnLongField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(long, long)",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{long.class}));
    }

    @Test
    public void testUnequalObjectsOnLongArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(long[], long[])",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{long[].class}));
    }

    @Test
    public void testUnequalObjectsOnCharField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(char, char)",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{char.class}));
    }

    @Test
    public void testUnequalObjectsOnCharArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(char[], char[])",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{char[].class}));
    }

    @Test
    public void testUnequalObjectsOnByteField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(byte, byte)",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{byte.class}));
    }

    @Test
    public void testUnequalObjectsOnByteArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(byte[], byte[])",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{byte[].class}));
    }

    @Test
    public void testUnequalObjectsOnFloatField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(float, float)",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{float.class}));
    }

    @Test
    public void testUnequalObjectsOnFloatArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(float[], float[])",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{float[].class}));
    }

    @Test
    public void testUnequalObjectsOnDoubleField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(double, double)",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{double.class}));
    }

    @Test
    public void testUnequalObjectsOnDoubleArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(double[], double[])",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{double[].class}));
    }

    @Test
    public void testUnequalObjectsOnObjectField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(Object, Object)",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{String.class}));
    }

    @Test
    public void testUnequalObjectsOnObjectArrayField(){
        assertFalse(NOT_EQUAL_MSG  + "problem in method append(Object[], Object[])",
                areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{String[].class}));

    }


    private boolean areHashCodesEqual(TestEntityObject obj1, TestEntityObject obj2, int superHashCode, Class<?>[] classes){
        HashcodeBuilder builder1 = HashcodeBuilder.createInstance(29, 31);
        HashcodeBuilder builder2 = HashcodeBuilder.createInstance(29, 31);
        builder1.appendSuper(superHashCode);
        builder2.appendSuper(superHashCode);
        for (Class<?> cls : classes) {
            Field[] fields = obj1.getClass().getFields();
            for (Field field : fields) {
                if (field.getType().equals(cls)) {
                    try {
                        if (cls.isPrimitive()) {
                            if (cls.equals(boolean.class)) {
                                builder1.append(field.getBoolean(obj1));
                                builder2.append(field.getBoolean(obj2));
                            } else if (cls.equals(byte.class)) {
                                builder1.append(field.getByte(obj1));
                                builder2.append(field.getByte(obj2));
                            } else if (cls.equals(char.class)) {
                                builder1.append(field.getChar(obj1));
                                builder2.append(field.getChar(obj2));
                            } else if (cls.equals(short.class)) {
                                builder1.append(field.getShort(obj1));
                                builder2.append(field.getShort(obj2));
                            } else if (cls.equals(int.class)) {
                                builder1.append(field.getInt(obj1));
                                builder2.append(field.getInt(obj2));
                            } else if (cls.equals(long.class)) {
                                builder1.append(field.getLong(obj1));
                                builder2.append(field.getLong(obj2));
                            } else if (cls.equals(float.class)) {
                                builder1.append(field.getFloat(obj1));
                                builder2.append(field.getFloat(obj2));
                            } else if (cls.equals(double.class)) {
                                builder1.append(field.getDouble(obj1));
                                builder2.append(field.getDouble(obj2));
                            }
                        } else {
                            builder1.append(field.get(obj1));
                            builder2.append(field.get(obj2));
                        }
                    } catch (IllegalAccessException e) {
                        continue;
                    }
                }
            }
        }
        System.out.println(builder1.hashCode() + " == " + builder2.hashCode());
        return builder1.getHashCode() == builder2.getHashCode();
    }

    private boolean areHashCodesEqualAllFields(TestEntityObject obj1, TestEntityObject obj2) {
        return areHashCodesEqual(obj1, obj2, 1, new Class<?>[]{
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
