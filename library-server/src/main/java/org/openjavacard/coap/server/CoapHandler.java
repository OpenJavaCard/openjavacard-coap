package org.openjavacard.coap.server;

import org.openjavacard.coap.core.Coap;
import org.openjavacard.coap.core.CoapException;
import org.openjavacard.coap.core.CoapRequest;
import org.openjavacard.coap.core.CoapResponse;

public interface CoapHandler extends Coap {

    void process(CoapRequest request, CoapResponse response) throws CoapException;

}
