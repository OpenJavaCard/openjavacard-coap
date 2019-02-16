package org.openjavacard.coap.californium;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.smartcardio.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(CardConnector.class);


    public static void main(String[] arguments) {
        Main m = new Main();
        try {
            m.run(arguments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(String[] arguments) throws Exception {
        CardTerminal terminal = findSingleTerminal(arguments[0]);
        Card card = terminal.connect("*");

        String command = arguments[1];

        CardConnector connector = new CardConnector(card);

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

    public CardTerminal findSingleTerminal(String prefix) {
        LOG.debug("findSingleTerminal()");
        List<CardTerminal> terminals = findTerminals(prefix);
        if (terminals.isEmpty()) {
            throw new Error("No terminals found");
        } else if (terminals.size() > 1) {
            if (prefix == null) {
                throw new Error("More than one terminal found");
            } else {
                throw new Error("More than one terminal found matching \"" + prefix + "\"");
            }
        }
        return terminals.get(0);
    }

    public List<CardTerminal> findTerminals(String prefix) {
        LOG.debug("findTerminals()");
        ArrayList<CardTerminal> found = new ArrayList<>();
        TerminalFactory tf = TerminalFactory.getDefault();
        CardTerminals ts = tf.terminals();
        try {
            List<CardTerminal> terminals = ts.list();
            for (CardTerminal terminal : terminals) {
                String name = terminal.getName();
                LOG.trace("terminal \"" + name + "\"");
                if (prefix == null || name.startsWith(prefix)) {
                    found.add(terminal);
                }
            }
        } catch (CardException e) {
            throw new Error("Error detecting terminals", e);
        }
        return found;
    }

}
