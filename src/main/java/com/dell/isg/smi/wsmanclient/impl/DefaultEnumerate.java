/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.impl;

import org.w3c.dom.Document;

import com.dell.isg.smi.wsmanclient.WSManBaseEnumerateCommand;
import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;


/**
 * The Class DefaultEnumerate.
 *
 * @param <T> the generic type
 */
public class DefaultEnumerate<T> extends WSManBaseEnumerateCommand<T> {

    private Enum<?> command = null;
    
    /**
     * Instantiates a new default enumerate.
     *
     * @param command the command
     */
    public DefaultEnumerate(Enum<?> command) {
        super();
        this.command = command;
    }

    /* (non-Javadoc)
     * @see com.dell.isg.smi.wsmanclient.IWSManCommand#parse(java.lang.String)
     */
    @Override
    public T parse(String xml) throws WSManException {
        Document tempDoc = WSManUtils.toDocument(xml);
        return (T) WSManUtils.toObjectMap(tempDoc);
    }

    /* (non-Javadoc)
     * @see com.dell.isg.smi.wsmanclient.WSManBaseEnumerateCommand#getCommandEnum()
     */
    @Override
    public Enum<?> getCommandEnum() {
        return command;
    }

}
