package org.openjavacard.coap.server;

import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import org.openjavacard.coap.core.Coap;
import org.openjavacard.coap.core.CoapException;
import org.openjavacard.coap.core.CoapRequest;
import org.openjavacard.coap.core.CoapResponse;

public class StaticDirectory extends CoapResource {

    private final CoapResource[] mChildren;

    public StaticDirectory(byte[] name, byte maxChildren) {
        super(name);
        mChildren = new CoapResource[maxChildren];
    }

    public void addChild(CoapResource child) {
        for(byte i = 0; i < mChildren.length; i++) {
            if(mChildren[i] == null) {
                mChildren[i] = child;
                child.setParent(this);
                return;
            }
        }
        ISOException.throwIt(ISO7816.SW_FILE_FULL);
    }

    public void removeChild(CoapResource child) {
        for(byte i = 0; i < mChildren.length; i++) {
            if(mChildren[i] == child) {
                mChildren[i] = null;
            }
        }
    }

    protected void processGet(CoapRequest request, CoapResponse response) throws CoapException {
        response.setCode(Coap.RESPONSE_OK);
        response.setContentFormat(Coap.MEDIA_TYPE_TEXT_PLAIN);
    }

}
