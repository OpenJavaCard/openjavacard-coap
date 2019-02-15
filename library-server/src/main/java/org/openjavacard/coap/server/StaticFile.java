package org.openjavacard.coap.server;

import org.openjavacard.coap.core.Coap;
import org.openjavacard.coap.core.CoapException;
import org.openjavacard.coap.core.CoapRequest;
import org.openjavacard.coap.core.CoapResponse;

public class StaticFile extends CoapResource {

    private short mContentFormat;

    private byte[] mContent;

    public StaticFile(byte[] name, short contentFormat, byte[] content) {
        super(name);
        mContentFormat = contentFormat;
        mContent = content;
    }

    protected void processGet(CoapRequest request, CoapResponse response) throws CoapException {
        response.setCode(Coap.RESPONSE_OK);
        response.setContentFormat(mContentFormat);
    }

}
