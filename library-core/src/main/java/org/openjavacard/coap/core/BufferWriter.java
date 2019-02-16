package org.openjavacard.coap.core;

import javacard.framework.Util;

public class BufferWriter extends BufferBase {

    public BufferWriter() {
    }

    public void writeByte(byte[] buf, byte value) {
        short off = mVars[VAR_CURRENT_OFF];
        advance((short)1);
        buf[off] = value;
    }

    public void writeShort(byte[] buf, short value) {
        short off = mVars[VAR_CURRENT_OFF];
        advance((short)2);
        Util.setShort(buf, off, value);
    }

    public void writeBytes(byte[] buf, byte[] src) {
        writeBytes(buf, src, (short)0, (short)src.length);
    }

    public void writeBytes(byte[] buf, byte[] src, short srcOff, short srcLen) {
        short off = mVars[VAR_CURRENT_OFF];
        advance(srcLen);
        Util.arrayCopyNonAtomic(src, srcOff, buf, off, srcLen);
    }

    public short finish(byte[] buf) {
        short len = (short)(getCurrentOffset() - getStartOffset());
        return len;
    }

}
