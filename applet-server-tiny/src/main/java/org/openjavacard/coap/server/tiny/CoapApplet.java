package org.openjavacard.coap.server.tiny;

import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import org.openjavacard.coap.core.Coap;
import org.openjavacard.coap.server.CoapServer;
import org.openjavacard.coap.server.StaticDirectory;
import org.openjavacard.coap.server.StaticFile;

public class CoapApplet extends Applet implements ISO7816, Coap {

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
        CoapApplet applet = new CoapApplet(buf, offAD, lenAD);

        // register the applet
        applet.register(buf, offAID, lenAID);
    }

    private final CoapServer mServer;

    private CoapApplet(byte[] buf, short off, byte len) {
        mServer = new CoapServer();

        byte[] data = new byte[] {'h', 'e', 'l', 'l', 'o'};
        if(len > 0) {
            data = new byte[len];
            for (byte i = 0; i < len; i++) {
                data[i] = buf[(short)(off + i)];
            }
        }

        StaticDirectory root = mServer.getRootDirectory();
        StaticFile index = new StaticFile(
                new byte[] {'i', 'n', 'd', 'e', 'x'},
                Coap.MEDIA_TYPE_TEXT_PLAIN, data);
        root.addChild(index);
    }

    public void process(APDU apdu) throws ISOException {
        byte[] buf = apdu.getBuffer();
        byte cla = buf[OFFSET_CLA];
        byte ins = buf[OFFSET_INS];

        if(selectingApplet()) {
            return;
        }

        if(apdu.isSecureMessagingCLA()) {
            ISOException.throwIt(SW_SECURE_MESSAGING_NOT_SUPPORTED);
        }

        if(cla != CLA_COAP) {
            ISOException.throwIt(SW_CLA_NOT_SUPPORTED);
        }

        if(ins != INS_COAP) {
            ISOException.throwIt(SW_INS_NOT_SUPPORTED);
        }

        mServer.process(apdu);

    }

}
