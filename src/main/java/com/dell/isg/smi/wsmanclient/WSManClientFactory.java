/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient;

import java.net.URL;

import com.dell.isg.smi.wsmanclient.impl.DefaultWSManClientFactory;
import com.dell.isg.smi.wsmanclient.impl.IWSManClientFactory;

/**
 * {@code WSManClientFactory} is a factory class for obtaining {@link IWSManClient} instances.
 */
public class WSManClientFactory {
    private static final IWSManClientFactory factory = new DefaultWSManClientFactory();


    private WSManClientFactory() {
    }


    /**
     * Returns an {@link IWSManClient} instance.
     *
     * @param ip The iDRAC IP address.
     * @param username The iDRAC username.
     * @param password The iDRAC password.
     * @return The {@link IWSManClient}
     */
    public static IWSManClient getClient(String ip, String username, String password) {
        return factory.getClient(ip, username, password);
    }


    /**
     * Returns an {@link IWSManClient} instance.
     *
     * @param ip The iDRAC IP address.
     * @param port The port that the iDRAC WS-Management service listens on.
     * @param username The iDRAC username.
     * @param password The iDRAC password.
     * @return The {@link IWSManClient}
     */
    public static IWSManClient getClient(String ip, int port, String username, String password) {
        return factory.getClient(ip, port, username, password);
    }


    /**
     * Returns an {@link IWSManClient} instance.
     *
     * @param connectionUrl The connection URL for the iDRAC WS-Management service.
     * @param username The iDRAC username.
     * @param password The iDRAC password.
     * @return The {@link IWSManClient}
     */
    public static IWSManClient getClient(URL connectionUrl, String username, String password) {
        return factory.getClient(connectionUrl, username, password);
    }

}
