package org.openjavacard.coap.server;

import javacard.framework.APDU;
import javacard.framework.ISO7816;
import javacard.framework.Util;
import org.openjavacard.coap.core.Coap;
import org.openjavacard.coap.core.CoapException;
import org.openjavacard.coap.core.CoapRequest;
import org.openjavacard.coap.core.CoapResponse;

public class CoapServer implements Coap {

    private StaticDirectory mRootDirectory;

    private CoapException mException;

    private CoapRequest mRequest;
    private CoapResponse mResponse;

    public CoapServer() {
        mRootDirectory = new StaticDirectory(null, (byte)8);
        mException = new CoapException();
        mRequest = new CoapRequest();
        mResponse = new CoapResponse();
    }

    public StaticDirectory getRootDirectory() {
        return mRootDirectory;
    }

    public void process(APDU apdu) {
        byte[] buf = apdu.getBuffer();
        byte lc = buf[ISO7816.OFFSET_CDATA];

        // reset data structures
        mRequest.reset();
        mResponse.reset();

        try {
            // parse the request
            mRequest.parse(buf, ISO7816.OFFSET_CDATA, lc);
            // find handling resource
            CoapResource resource = findResource(mRequest);
            if(resource != null) {
                // process the request
                resource.process(mRequest, mResponse);
            } else {
                mResponse.setCode(CLIENT_NOT_FOUND);
            }
        } catch (CoapException e) {
            mResponse.reset();
            mResponse.setCode(e.getCode());
        } catch (Throwable t) {
            mResponse.reset();
            mResponse.setCode(SERVER_INTERNAL_ERROR);
        }

        // copy the token
        mResponse.setToken(mRequest.getToken());

        // serialize the response
        short lr = mResponse.finish(buf, (short)0, (short)buf.length);

        // send the response
        apdu.setOutgoingAndSend((short)0, lr);
    }

    private CoapResource findResource(CoapRequest request) throws CoapException {
        CoapResource res = mRootDirectory;
        byte count = request.getUriPathCount();
        for(byte i = 0; i < count; i++) {
            byte[] element = request.getUriPath(i);
            CoapResource next = res.find(element);
            if(next == null) {
                mException.throwResponse(CLIENT_NOT_FOUND);
            }
        }
        return res;
    }

}
