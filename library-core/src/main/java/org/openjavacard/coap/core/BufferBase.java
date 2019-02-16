package org.openjavacard.coap.core;

import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.JCSystem;

public abstract class BufferBase {

    protected final short[] mVars;
    protected final static byte VAR_START_OFFSET = 0;
    protected final static byte VAR_START_LENGTH = 1;
    protected final static byte VAR_CURRENT_OFF = 2;
    protected final static byte VAR_CURRENT_LEN = 3;
    protected final static byte NUM_VARS = 4;

    protected BufferBase() {
        mVars = JCSystem.makeTransientShortArray(NUM_VARS, JCSystem.CLEAR_ON_DESELECT);
    }

    public short getStartOffset() {
        return mVars[VAR_START_OFFSET];
    }

    public short getCurrentOffset() {
        return mVars[VAR_CURRENT_OFF];
    }

    public short getTotalLength() {
        return mVars[VAR_START_LENGTH];
    }

    public short getRemainingLength() {
        return mVars[VAR_CURRENT_LEN];
    }

    public boolean hasRemaining(short nbytes) {
        return mVars[VAR_CURRENT_LEN] >= nbytes;
    }

    public void begin(byte[] buf, short off, short len) {
        checkRange(buf, off, len);
        mVars[VAR_START_OFFSET] = off;
        mVars[VAR_START_LENGTH] = len;
        mVars[VAR_CURRENT_OFF] = off;
        mVars[VAR_CURRENT_LEN] = len;
    }

    protected void advance(short nbytes) {
        checkAvailable(nbytes);
        mVars[VAR_CURRENT_OFF] += nbytes;
        mVars[VAR_CURRENT_LEN] -= nbytes;
    }

    protected void checkAvailable(short nbytes) {
        if(nbytes > mVars[VAR_CURRENT_LEN]) {
            ISOException.throwIt(ISO7816.SW_UNKNOWN);
        }
    }

    protected static void checkRange(byte[] buf, short offset, short length) {
        short end = (short)(offset + length);
        if(end < 0) {
            ISOException.throwIt(ISO7816.SW_UNKNOWN);
        }
        if(end > buf.length) {
            ISOException.throwIt(ISO7816.SW_UNKNOWN);
        }
    }

}
