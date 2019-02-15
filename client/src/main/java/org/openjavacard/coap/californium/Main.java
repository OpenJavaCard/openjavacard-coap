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

        CardConnector connector = new CardConnector(null);

        CoapEndpoint endpoint = new CoapEndpoint.Builder()
                    .setConnector(connector)
                    .build();

        CoapClient client = new CoapClient();
        client.setEndpoint(endpoint);

        if(command.equals("get")) {
            client.setURI("coap+tcp://127.0.0.1/index");
            client.get();
        }

        if(command.equals("proxy")) {

        }
    }

}
