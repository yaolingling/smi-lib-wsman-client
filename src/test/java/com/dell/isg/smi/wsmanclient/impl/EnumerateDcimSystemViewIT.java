/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.impl;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.Test;

import com.dell.isg.smi.wsmanclient.WSManConstants.WSManClassEnum;
import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsmanclient.impl.DefaultEnumerate;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EnumerateDcimSystemViewIT extends BaseCmdIT
{
	@Test
	public void shouldFindKnownStringValueInJSonObject() throws IOException, WSManException, Exception
	{		
		Object systemViewObject =  client.execute(new DefaultEnumerate<Object>(WSManClassEnum.DCIM_SystemView));
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(systemViewObject);
		 
		JSONObject jsonObject = new JSONObject(jsonInString);
		String serviceTag = jsonObject.getString("ServiceTag");
		
		assertNotNull("Service tag is null", serviceTag );
		assertNotSame("Service tag is empty", "", serviceTag);
	}
}