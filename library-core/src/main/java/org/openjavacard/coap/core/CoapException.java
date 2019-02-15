package org.openjavacard.coap.core;

public class CoapException extends Exception {

    private byte mCode;

    public byte getCode() {
        return mCode;
    }

    public void throwResponse(byte code) throws CoapException {
        mCode = code;
        throw this;
    }

}
