package org.openjavacard.coap.server.tiny;

import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import org.openjavacard.coap.server.CoapServer;

public class CoapApplet extends Applet implements ISO7816 {

    public static void install(byte[] buf, short off, byte len) {
        short pos = off;
        // find AID
        byte  lenAID = buf[pos++];
        short offAID = pos;
        pos += lenAID;
        // find control information (ignored)
        byte  lenCI = buf[pos++];
        short offCI = pos;
        pos += lenCI;
        // find applet data
        byte  lenAD = buf[pos++];
        short offAD = pos;
        pos += lenAD;

        // instantiate and initialize the applet
        CoapApplet applet = new CoapApplet();

        // register the applet
        applet.register(buf, offAID, lenAID);
    }

    private CoapServer mServer;

    private CoapApplet() {
        mServer = new CoapServer();
    }

    public void process(APDU apdu) throws ISOException {
        byte[] buf = apdu.getBuffer();
        byte cla = buf[OFFSET_CLA];
        byte ins = buf[OFFSET_INS];

        if(selectingApplet()) {
            return;
        }

        mServer.process(apdu);

    }

}
