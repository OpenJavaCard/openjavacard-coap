package org.openjavacard.coap.server;

import org.openjavacard.coap.core.CoapException;
import org.openjavacard.coap.core.CoapRequest;
import org.openjavacard.coap.core.CoapResponse;

public abstract class CoapHandler {

    public abstract byte process(CoapRequest request, CoapResponse response) throws CoapException;

}
