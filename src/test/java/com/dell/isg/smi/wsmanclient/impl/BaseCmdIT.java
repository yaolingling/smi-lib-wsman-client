/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.impl;

import org.junit.After;
import org.junit.Before;

import com.dell.isg.smi.wsmanclient.WSManClientFactory;
import com.dell.isg.smi.wsmanclient.IWSManClient;


public abstract class BaseCmdIT
{
	protected IWSManClient client;

 @Before
 public void setUp()
 {
  String ip = "100.68.123.160";
  	client = WSManClientFactory.getClient(ip, "root", "calvin");
 }

 @After
 public void tearDown()
 {
  client.close();
 }
}
