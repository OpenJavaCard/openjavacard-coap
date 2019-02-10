package org.openjavacard.coap.server;

import javacard.framework.APDU;
import javacard.framework.ISO7816;
import org.openjavacard.coap.core.Coap;
import org.openjavacard.coap.core.CoapException;
import org.openjavacard.coap.core.CoapRequest;
import org.openjavacard.coap.core.CoapResponse;

public class CoapServer implements ISO7816, Coap {

    private CoapException mException;

    private CoapRequest mRequest;
    private CoapResponse mResponse;

    public CoapServer() {
        mException = new CoapException();
        mRequest = new CoapRequest();
        mResponse = new CoapResponse();
    }

    public void process(APDU apdu) {
        byte[] buf = apdu.getBuffer();
        byte lc = buf[OFFSET_LC];

        try {
            mRequest.parse(buf, OFFSET_CDATA, lc);

            byte version = mRequest.getVersion();
            byte code = mRequest.getCode();

            if (version != VERSION_RFC7252) {
                mException.throwResponse(CLIENT_BAD_REQUEST);
            }

        } catch (CoapException ex) {

        }
    }

}
