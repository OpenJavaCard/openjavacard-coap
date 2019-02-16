package org.openjavacard.coap.core;

import javacard.framework.JCSystem;

public class Array {

    private static void checkRange(short arrayLength, short offset, short length) {
        short end = (short)(offset + length);
        if(end < 0) {
        }
        if(end > arrayLength) {
        }
    }

    private static void checkRange(byte[] array, short offset, short length) {
        checkRange((short)array.length, offset, length);
    }

    private static void checkRange(short[] array, short offset, short length) {
        checkRange((short)array.length, offset, length);
    }

    public static byte[] makeByteArray(short size, byte type) {
        if(type == JCSystem.MEMORY_TYPE_PERSISTENT) {
            return new byte[size];
        } else {
            return JCSystem.makeTransientByteArray(size, type);
        }
    }

    public static short[] makeShortArray(short size, byte type) {
        if(type == JCSystem.MEMORY_TYPE_PERSISTENT) {
            return new short[size];
        } else {
            return JCSystem.makeTransientShortArray(size, type);
        }
    }

    public static Object[] makeObjectArray(short size, byte type) {
        if(type == JCSystem.MEMORY_TYPE_PERSISTENT) {
            return new Object[size];
        } else {
            return JCSystem.makeTransientObjectArray(size, type);
        }
    }

    public static boolean rangeEqual(byte[] aArray, short aOffset, byte[] bArray, short bOffset, short len) {
        checkRange(aArray, aOffset, len);
        checkRange(bArray, aOffset, len);
        boolean res = true;
        for(short i = 0; i < len; i++) {
            short ai = (short)(aOffset + i);
            short bi = (short)(bOffset + i);
            res &= (aArray[ai] == bArray[bi]);
        }
        return res;
    }

    public static boolean rangeEqual(short[] aArray, short aOffset, byte[] bArray, short bOffset, short len) {
        checkRange(aArray, aOffset, len);
        checkRange(bArray, aOffset, len);
        boolean res = true;
        for(short i = 0; i < len; i++) {
            short ai = (short)(aOffset + i);
            short bi = (short)(bOffset + i);
            res &= (aArray[ai] == bArray[bi]);
        }
        return res;
    }

    public static void rangeFill(byte[] array, short off, short len, byte value) {
        checkRange(array, off, len);
        short end = (short)(off + len);
        for(short i = off; i < end; i++) {
            array[i] = value;
        }
    }

    public static void rangeFill(short[] array, short off, short len, short value) {
        checkRange(array, off, len);
        short end = (short)(off + len);
        for(short i = off; i < end; i++) {
            array[i] = value;
        }
    }

    public static void rangeClear(byte[] array, short off, short len) {
        rangeFill(array, off, len, (byte)0);
    }

    public static void rangeClear(short[] array, short off, short len) {
        rangeFill(array, off, len, (short)0);
    }

    public static void rangeCopy(byte[] dstArray, short dstOff, byte[] srcArray, short srcOff, short length) {
        checkRange(dstArray, dstOff, length);
        checkRange(srcArray, srcOff, length);
        for(short i = 0; i < length; i++) {
            short di = (short)(dstOff + i);
            short si = (short)(srcOff + i);
            dstArray[di] = srcArray[si];
        }
    }

    public static void rangeCopy(short[] dstArray, short dstOff, short[] srcArray, short srcOff, short length) {
        checkRange(dstArray, dstOff, length);
        checkRange(srcArray, srcOff, length);
        for(short i = 0; i < length; i++) {
            short di = (short)(dstOff + i);
            short si = (short)(srcOff + i);
            dstArray[di] = srcArray[si];
        }
    }

}
