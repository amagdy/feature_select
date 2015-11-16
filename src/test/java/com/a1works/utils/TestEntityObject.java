package com.a1works.utils;

/**
 * Created by Ahmed Magdy Ezzeldin <a.magdy@a1works.com> on 15/11/15.
 */
public class TestEntityObject {

        public boolean boolValue;
        public boolean[] boolArrayValue;

        public int intValue;
        public int[] intArrayValue;

        public short shortValue;
        public short[] shortArrayValue;

        public long longValue;
        public long[] longArrayValue;

        public char charValue;
        public char[] charArrayValue;

        public byte byteValue;
        public byte[] byteArrayValue;

        public float floatValue;
        public float[] floatArrayValue;

        public double doubleValue;
        public double[] doubleArrayValue;

        public String stringValue;
        public String[] stringArrayValue;

        public static TestEntityObject createInstanceWithData(boolean boolValue,
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


        public static TestEntityObject createInstanceWithData1(){
                TestEntityObject obj = createInstanceWithData(
                        true,
                        new boolean[]{true, false, true},
                        1,
                        new int[]{1, 2, 3},
                        (short) 2,
                        new short[]{4, 5, 6},
                        (long) 3,
                        new long[]{7, 8, 9},
                        'a',
                        new char[]{'a', 'b', 'c'},
                        (byte) 1,
                        new byte[]{1, 2, 3},
                        (float) 10.0,
                        new float[]{(float) 11.0, (float) 12.0, (float) 13.0},
                        14.0,
                        new double[]{15.0, 16.0, 17.0},
                        "Name1",
                        new String[]{"name1", "first1", "last1"}
                );
                return obj;
        }

        public static TestEntityObject createInstanceWithData2(){
                TestEntityObject obj = createInstanceWithData(
                        false,
                        new boolean[]{false, false, false},
                        11,
                        new int[]{11, 12, 13},
                        (short) 12,
                        new short[]{14, 15, 16},
                        (long) 13,
                        new long[]{17, 18, 19},
                        'b',
                        new char[]{'e', 'f', 'g'},
                        (byte) 11,
                        new byte[]{11, 12, 13},
                        (float) 110.0,
                        new float[]{(float) 111.0, (float) 112.0, (float) 113.0},
                        114.0,
                        new double[]{115.0, 116.0, 117.0},
                        "Name2",
                        new String[]{"name2", "first2", "last2"}
                );
                return obj;
        }
}
