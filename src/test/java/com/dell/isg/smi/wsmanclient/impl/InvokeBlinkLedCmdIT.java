/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved. 
 */
package com.dell.isg.smi.wsmanclient.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsmanclient.impl.cmd.InvokeBlinkLedCmd;
import com.dell.isg.smi.wsmanclient.model.InvokeCmdResponse;

public class InvokeBlinkLedCmdIT extends BaseCmdIT {
	@Test
	public void testExecute() throws IOException, WSManException {
		try {
			// Test blinking the led 
			InvokeCmdResponse execute = client.execute(new InvokeBlinkLedCmd(true));
			assertNotNull(execute);
			assertTrue("Invalid return code.", 0 == execute.getReturnValue());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
