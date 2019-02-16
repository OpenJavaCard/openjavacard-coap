package org.openjavacard.coap.core;

import javacard.framework.JCSystem;

public class CoapResponse implements Coap {

    public static final byte MAX_LOCATION_PATH_COUNT = 4;
    public static final byte MAX_LOCATION_QUERY_COUNT = 8;

    private final BufferWriter mWriter;

    private byte mCode;

    private short mContentFormat;

    private byte[] mToken;
    private Object[] mLocationPath;
    private Object[] mLocationQuery;

    public CoapResponse() {
        mWriter = new BufferWriter();
        mLocationPath = JCSystem.makeTransientObjectArray(MAX_LOCATION_PATH_COUNT, JCSystem.CLEAR_ON_DESELECT);
        mLocationQuery = JCSystem.makeTransientObjectArray(MAX_LOCATION_QUERY_COUNT, JCSystem.CLEAR_ON_DESELECT);
    }

    public byte getCode() {
        return mCode;
    }

    public void setCode(byte code) {
        mCode = code;
    }

    public byte[] getToken() {
        return mToken;
    }

    public void setToken(byte[] token) {
        mToken = token;
    }

    public short getContentFormat() {
        return mContentFormat;
    }

    public void setContentFormat(short contentFormat) {
        mContentFormat = contentFormat;
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
        byte b0len = 14;
        byte b0tkl = 0;
        if(mToken != null) {
            b0tkl = (byte)mToken.length;
        }
        byte b0 = (byte)((b0len << 4) | b0tkl);
        short l = 0;

        mWriter.begin(buf, off, len);
        mWriter.writeByte(buf, b0);
        mWriter.writeShort(buf, l);
        mWriter.writeByte(buf, mCode);
        if(b0tkl > 0) {
            mWriter.writeBytes(buf, mToken);
        }
        return mWriter.finish(buf);
    }

}
