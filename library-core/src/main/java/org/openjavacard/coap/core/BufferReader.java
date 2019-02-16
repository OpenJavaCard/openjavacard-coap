package org.openjavacard.coap.core;

import javacard.framework.JCSystem;
import javacard.framework.Util;

public class BufferReader extends BufferBase {

    public BufferReader() {
    }

    public byte readByte(byte[] buf) {
        short off = mVars[VAR_CURRENT_OFF];
        advance((short)1);
        return buf[off];
    }

    public short readShort(byte[] buf) {
        short off = mVars[VAR_CURRENT_OFF];
        advance((short)2);
        return Util.getShort(buf, off);
    }

    public byte[] readBytesPersistent(byte[] buf, short nbytes) {
        short off = mVars[VAR_CURRENT_OFF];
        byte[] res = new byte[nbytes];
        advance(nbytes);
        Util.arrayCopyNonAtomic(buf, off, res, (short)0, nbytes);
        return res;
    }

    public byte[] readBytesTransient(byte[] buf, short nbytes, byte type) {
        short off = mVars[VAR_CURRENT_OFF];
        byte[] res = JCSystem.makeTransientByteArray(nbytes, type);
        advance(nbytes);
        Util.arrayCopyNonAtomic(buf, off, res, (short)0, nbytes);
        return res;
    }

    public void readBytesInto(byte[] buf, byte[] dstBuf, short dstOff, short nbytes) {
        short off = mVars[VAR_CURRENT_OFF];
        advance(nbytes);
        Util.arrayCopyNonAtomic(buf, off, dstBuf, dstOff, nbytes);
    }

    public void skipByte(byte[] buf) {
        readByte(buf);
    }

    public void skipShort(byte[] buf) {
        readShort(buf);
    }

    public void skipBytes(byte[] buf, short nbytes) {
        checkAvailable(nbytes);
        advance(nbytes);
    }

}
