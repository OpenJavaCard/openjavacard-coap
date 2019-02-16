package org.openjavacard.coap.californium;

import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.elements.*;
import org.openjavacard.iso.ISO7816;
import org.openjavacard.iso.SWException;
import org.openjavacard.util.APDUUtil;
import org.openjavacard.util.HexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.smartcardio.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class CardConnector implements Connector {

    private static final Logger LOG = LoggerFactory.getLogger(CardConnector.class);

    private Card mCard;
    private CardChannel mChannel;

    private RawDataChannel mMessageHandler;
    private EndpointContextMatcher mEndpointMatcher;

    public CardConnector(Card card) {
        mCard = card;
    }

    @Override
    public String getProtocol() {
        return CoAP.PROTOCOL_TCP;
    }

    @Override
    public InetSocketAddress getAddress() {
        try {
            InetAddress local = InetAddress.getLocalHost();
            return new InetSocketAddress(local, CoAP.DEFAULT_COAP_PORT);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Could not get local host!?", e);
        }
    }

    @Override
    public void setRawDataReceiver(RawDataChannel messageHandler) {
        LOG.trace("setRawDataReceiver()");
        mMessageHandler = messageHandler;
    }

    @Override
    public void setEndpointContextMatcher(EndpointContextMatcher matcher) {
        LOG.trace("setEndpointContextMatcher()");
        mEndpointMatcher = matcher;
    }

    @Override
    public void start() throws IOException {
        LOG.debug("start()");
        try {
            mChannel = mCard.getBasicChannel();
            performSelectByName(HexUtil.hexToBytes("D27600017710021201000101"));
        } catch (CardException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void stop() {
        LOG.debug("stop()");
        if(mChannel != null) {
            try {
                mChannel.close();
            } catch (CardException e) {
                LOG.info("error closing channel", e);
            }
            mChannel = null;
        }
    }

    @Override
    public void destroy() {
        LOG.info("destroy()");
    }

    @Override
    public void send(RawData msg) {
        LOG.info("send(" + HexUtil.bytesToHex(msg.getBytes()) + ")");

        try {
            EndpointContext ctx = msg.getEndpointContext();

            // construct the command
            CommandAPDU capdu = APDUUtil.buildCommand((byte)0x80, (byte)0x80, msg.getBytes());

            // perform the exchange
            ResponseAPDU rapdu = transmitAndCheck(capdu);

            // notify the library about the message context
            msg.onContextEstablished(ctx);
            // notify the library that the request was sent
            msg.onSent();
            // construct response buffer
            RawData response = RawData.inbound(rapdu.getData(), ctx, false);
            // deliver the response
            mMessageHandler.receiveData(response);
        } catch (CardException e) {
            LOG.warn("error", e);
            msg.onError(e);
        }
    }

    public ResponseAPDU performSelectByName(byte[] name) throws CardException {
        return performSelectByName(name, true);
    }

    public ResponseAPDU performSelectByName(byte[] name, boolean first) throws CardException {
        byte p2 = first ? ISO7816.SELECT_P2_FIRST_OR_ONLY : ISO7816.SELECT_P2_NEXT;
        return performSelect(ISO7816.SELECT_P1_BY_NAME, p2, name);
    }

    private ResponseAPDU performSelect(byte p1, byte p2, byte[] data) throws CardException {
        CommandAPDU scapdu = APDUUtil.buildCommand(
                ISO7816.CLA_ISO7816, ISO7816.INS_SELECT,
                p1, p2, data);
        return transmitAndCheck(scapdu);
    }

    public ResponseAPDU transmit(CommandAPDU command) throws CardException {
        LOG.debug("apdu > " + APDUUtil.toString(command));
        ResponseAPDU response = mChannel.transmit(command);
        LOG.debug("apdu < " + APDUUtil.toString(response));
        return response;
    }

    public ResponseAPDU transmitAndCheck(CommandAPDU command) throws CardException {
        ResponseAPDU response = transmit(command);
        checkResponse(response);
        return response;
    }

    private void checkResponse(ResponseAPDU response) throws CardException {
        int sw = response.getSW();
        if (sw != ISO7816.SW_NO_ERROR) {
            throw new SWException("Error in transaction", sw);
        }
    }

}
