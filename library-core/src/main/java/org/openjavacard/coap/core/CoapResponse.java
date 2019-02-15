package org.openjavacard.coap.core;

import javacard.framework.JCSystem;

public class CoapResponse implements Coap {

    public static final byte MAX_LOCATION_PATH_COUNT = 4;
    public static final byte MAX_LOCATION_QUERY_COUNT = 8;

    private byte mCode;

    private short mContentFormat;

    private Object[] mLocationPath;
    private Object[] mLocationQuery;

    public CoapResponse() {
        mLocationPath = JCSystem.makeTransientObjectArray(MAX_LOCATION_PATH_COUNT, JCSystem.CLEAR_ON_RESET);
        mLocationQuery = JCSystem.makeTransientObjectArray(MAX_LOCATION_QUERY_COUNT, JCSystem.CLEAR_ON_RESET);
    }

    public byte getCode() {
        return mCode;
    }

    public void setCode(byte mCode) {
        mCode = mCode;
    }

    public short getContentFormat() {
        return mContentFormat;
    }

    public void setContentFormat(short mContentFormat) {
        mContentFormat = mContentFormat;
    }

    public void reset() {
        mCode = 0;
        mContentFormat = 0;
        for(byte i = 0; i < mLocationPath.length; i++) {
            mLocationPath[i] = null;
        }
        for(byte i = 0; i < mLocationQuery.length; i++) {
            mLocationQuery[i] = null;
        }
    }

    public short finish(byte[] buf, short off, short len) {
        return (short)0;
    }

}
