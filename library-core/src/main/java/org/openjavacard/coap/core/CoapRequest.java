package org.openjavacard.coap.core;

import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.JCSystem;

public class CoapRequest {

    public static final byte MAX_URI_PATH_COUNT = 4;
    public static final byte MAX_URI_QUERY_COUNT = 8;

    private final BufferReader mReader;

    private final short[]  mVars;
    private final static byte VAR_CODE = 0;
    private final static byte VAR_ACCEPT = 1;
    private final static byte VAR_URI_PORT = 2;
    private final static byte VAR_URI_PATH_COUNT = 3;
    private final static byte VAR_URI_QUERY_COUNT = 4;
    private final static byte NUM_VARS = 5;

    private final Object[] mRefs;
    private final static byte REF_TOKEN = 0;
    private final static byte REF_URI_HOST = 1;
    private final static byte NUM_REFS = 2;

    private final Object[] mUriPath;
    private final Object[] mUriQuery;

    public CoapRequest() {
        mReader = new BufferReader();
        mVars = JCSystem.makeTransientShortArray(NUM_VARS, JCSystem.CLEAR_ON_DESELECT);
        mRefs = JCSystem.makeTransientObjectArray(NUM_REFS, JCSystem.CLEAR_ON_DESELECT);
        mUriPath = JCSystem.makeTransientObjectArray(MAX_URI_PATH_COUNT, JCSystem.CLEAR_ON_DESELECT);
        mUriQuery = JCSystem.makeTransientObjectArray(MAX_URI_QUERY_COUNT, JCSystem.CLEAR_ON_DESELECT);
    }

    public byte getCode() {
        return (byte)mVars[VAR_CODE];
    }

    public byte[] getToken() {
        return (byte[])mRefs[REF_TOKEN];
    }

    public short getAccept() {
        return mVars[VAR_ACCEPT];
    }

    public byte[] getUriHost() {
        return (byte[])mRefs[REF_URI_HOST];
    }

    public short getUriPort() {
        return mVars[VAR_URI_PORT];
    }

    public byte getUriPathCount() {
        return (byte)mVars[VAR_URI_PATH_COUNT];
    }

    public byte[] getUriPath(byte idx) {
        Object obj = mUriPath[idx];
        return (byte[])obj;
    }

    public byte getUriQueryCount() {
        return (byte)mVars[VAR_URI_QUERY_COUNT];
    }

    public byte[] getUriQuery(byte idx) {
        Object obj = mUriQuery[idx];
        return (byte[])obj;
    }

    public void reset() {
        for(byte i = 0; i < mVars.length; i++) {
            mVars[i] = 0;
        }
        for(byte i = 0; i < mRefs.length; i++) {
            mRefs[i] = null;
        }
        for(byte i = 0; i < mUriPath.length; i++) {
            mUriPath[i] = null;
        }
        for(byte i = 0; i < mUriQuery.length; i++) {
            mUriQuery[i] = null;
        }
    }

    public void parse(byte[] buf, short off, short len) {
        mReader.begin(buf, off, len);

        // read and decode first byte
        byte b0 = mReader.readByte(buf);
        byte b0len = (byte)((b0 >> 4) & 0xF);
        byte b0tkl = (byte)(b0 & 0xF);

        // decode length
        short plen = 0;
        switch(b0len) {
            case 13:
                plen = mReader.readByte(buf);
                break;
            case 14:
                plen = mReader.readShort(buf);
                break;
            case 15:
                ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
                break;
            default:
                plen = b0len;
                break;
        }

        // read code
        mVars[VAR_CODE] = mReader.readByte(buf);

        // read token
        if(b0tkl > 0) {
            mRefs[REF_TOKEN] = mReader.readBytesTransient(buf, b0tkl, JCSystem.CLEAR_ON_DESELECT);
        }

        // check remaining length
        if(plen != mReader.getRemainingLength()) {
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
        }

        // read options

        // read payload
    }

}
