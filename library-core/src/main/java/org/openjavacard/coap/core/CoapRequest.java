package org.openjavacard.coap.core;

import javacard.framework.JCSystem;

public class CoapRequest {

    public static final byte MAX_URI_PATH_COUNT = 4;
    public static final byte MAX_URI_QUERY_COUNT = 8;

    private byte mCode;

    private short mAccept;

    private byte[]   mUriHost;
    private short    mUriPort;
    private Object[] mUriPath;
    private Object[] mUriQuery;

    public CoapRequest() {
        mUriPath = JCSystem.makeTransientObjectArray(MAX_URI_PATH_COUNT, JCSystem.CLEAR_ON_RESET);
        mUriQuery = JCSystem.makeTransientObjectArray(MAX_URI_QUERY_COUNT, JCSystem.CLEAR_ON_RESET);
    }

    public byte getCode() {
        return mCode;
    }

    public short getAccept() {
        return mAccept;
    }

    public byte[] getUriHost() {
        return mUriHost;
    }

    public short getUriPort() {
        return mUriPort;
    }

    public byte getUriPathCount() {
        return (byte)mUriPath.length;
    }

    public byte[] getUriPath(byte idx) {
        Object obj = mUriPath[idx];
        return (byte[])obj;
    }

    public byte getUriQueryCount() {
        return (byte)mUriQuery.length;
    }

    public byte[] getUriQuery(byte idx) {
        Object obj = mUriQuery[idx];
        return (byte[])obj;
    }

    public void reset() {
        mCode = (byte)0;
        mAccept = (short)0;
        mUriHost = null;
        mUriPort = Coap.PORT;
        for(byte i = 0; i < mUriPath.length; i++) {
            mUriPath[i] = null;
        }
        for(byte i = 0; i < mUriQuery.length; i++) {
            mUriQuery[i] = null;
        }
    }

    public void parse(byte[] buf, short off, short len) {
    }

}
