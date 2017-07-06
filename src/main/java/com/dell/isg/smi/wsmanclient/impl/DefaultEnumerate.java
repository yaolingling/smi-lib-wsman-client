package com.dell.isg.smi.wsmanclient.impl;

import org.w3c.dom.Document;

import com.dell.isg.smi.wsmanclient.WSManBaseEnumerateCommand;
import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;


public class DefaultEnumerate<T> extends WSManBaseEnumerateCommand<T> {

    private Enum<?> command = null;
    
    public DefaultEnumerate(Enum<?> command) {
        super();
        this.command = command;
    }

    @Override
    public T parse(String xml) throws WSManException {
        Document tempDoc = WSManUtils.toDocument(xml);
        return (T) WSManUtils.toObjectMap(tempDoc);
    }

    @Override
    public Enum<?> getCommandEnum() {
        return command;
    }

}
