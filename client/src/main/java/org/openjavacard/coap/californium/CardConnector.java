package org.openjavacard.coap.californium;

import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.elements.Connector;
import org.eclipse.californium.elements.EndpointContextMatcher;
import org.eclipse.californium.elements.RawData;
import org.eclipse.californium.elements.RawDataChannel;
import org.openjavacard.util.APDUUtil;
import org.openjavacard.util.HexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class CardConnector implements Connector {

    private static final Logger LOG = LoggerFactory.getLogger(CardConnector.class);

    private CardChannel mChannel;

    private RawDataChannel mMessageHandler;
    private EndpointContextMatcher mEndpointMatcher;

    public CardConnector(CardChannel channel) {
        mChannel = channel;
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
    }

    @Override
    public void stop() {
        LOG.debug("stop()");
    }

    @Override
    public void destroy() {
        LOG.info("destroy()");
    }

    @Override
    public void send(RawData msg) {
        LOG.info("stop()");
        LOG.info("send " + HexUtil.bytesToHex(msg.getBytes()));

        CommandAPDU capdu = APDUUtil.buildCommand((byte)0x80, (byte)0x80, msg.getBytes());
        try {
            ResponseAPDU rapdu = mChannel.transmit(capdu);
            // notify the library that the request was sent
            msg.onSent();
            // construct response buffer
            RawData response = RawData.inbound(
                    rapdu.getData(),
                    msg.getEndpointContext(),
                    false);
            // deliver the response
            mMessageHandler.receiveData(response);
        } catch (CardException e) {
            msg.onError(e);
        }
    }

}
