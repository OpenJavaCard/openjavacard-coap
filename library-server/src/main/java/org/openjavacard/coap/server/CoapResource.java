package org.openjavacard.coap.server;

import org.openjavacard.coap.core.Coap;
import org.openjavacard.coap.core.CoapException;
import org.openjavacard.coap.core.CoapRequest;
import org.openjavacard.coap.core.CoapResponse;

public abstract class CoapResource implements CoapHandler {

    private final byte[] mName;

    private CoapResource mParent;

    protected CoapResource(byte[] name) {
        mName = name;
    }

    public byte[] getName() {
        return mName;
    }

    public CoapResource getParent() {
        return mParent;
    }

    public void setParent(CoapResource mParent) {
        this.mParent = mParent;
    }

    public CoapResource find(byte[] childName) {
        return null;
    }

    public void process(CoapRequest request, CoapResponse response) throws CoapException {
        short code = request.getCode();
        switch (code) {
            case REQUEST_GET:
                processGet(request, response);
                break;
            case REQUEST_POST:
                processPost(request, response);
                break;
            case REQUEST_PUT:
                processPut(request, response);
                break;
            case REQUEST_DELETE:
                processDelete(request, response);
                break;
            default:
                response.setCode(Coap.CLIENT_METHOD_NOT_ALLOWED);
                break;
        }
    }

    protected void processGet(CoapRequest request, CoapResponse response) throws CoapException {
        response.setCode(Coap.CLIENT_METHOD_NOT_ALLOWED);
    }

    protected void processPost(CoapRequest request, CoapResponse response) throws CoapException {
        response.setCode(Coap.CLIENT_METHOD_NOT_ALLOWED);
    }

    protected void processPut(CoapRequest request, CoapResponse response) throws CoapException {
        response.setCode(Coap.CLIENT_METHOD_NOT_ALLOWED);
    }

    protected void processDelete(CoapRequest request, CoapResponse response) throws CoapException {
        response.setCode(Coap.CLIENT_METHOD_NOT_ALLOWED);
    }

}
