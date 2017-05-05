/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.impl;

import java.net.URL;

import com.dell.isg.smi.wsmanclient.IWSManClient;

public class DefaultWSManClientFactory implements IWSManClientFactory {
    @Override
    public IWSManClient getClient(String ip, String username, String password) {
        return new DefaultWSManClient(ip, username, password);
    }


    @Override
    public IWSManClient getClient(String ip, int port, String username, String password) {
        return new DefaultWSManClient(ip, port, username, password);
    }


    @Override
    public IWSManClient getClient(URL connectionUrl, String username, String password) {
        return new DefaultWSManClient(connectionUrl, username, password);
    }
}
