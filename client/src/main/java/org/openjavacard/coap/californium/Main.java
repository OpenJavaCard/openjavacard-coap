package org.openjavacard.coap.californium;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.network.CoapEndpoint;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] arguments) {
        String command = arguments[0];

        try {
            InetSocketAddress address = new InetSocketAddress(
                    InetAddress.getLocalHost(),
                    CoAP.DEFAULT_COAP_PORT
            );

            CardConnector connector = new CardConnector(null);

            CoapEndpoint endpoint = new CoapEndpoint.Builder()
                        .setConnector(connector)
                        .build();

            CoapClient client = new CoapClient();
            client.setEndpoint(endpoint);

            if(command.equals("get")) {
                client.setURI("coap://127.0.0.1/index");
                client.get();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
