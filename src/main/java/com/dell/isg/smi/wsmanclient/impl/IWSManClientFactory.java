/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.impl;

import java.net.URL;

import com.dell.isg.smi.wsmanclient.IWSManClient;

public interface IWSManClientFactory {
    IWSManClient getClient(String ip, String username, String password);


    IWSManClient getClient(String ip, int port, String username, String password);


    IWSManClient getClient(URL connectionUrl, String username, String password);
}
