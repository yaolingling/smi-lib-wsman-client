package com.dell.isg.smi.wsmanclient.impl;

import org.w3c.dom.Document;

import com.dell.isg.smi.wsmanclient.WSManBaseEnumerateCommand;
import com.dell.isg.smi.wsmanclient.WSManConstants.WSManClassEnum;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.dell.isg.smi.wsmanclient.WSManException;

public class DefaultEnumerate<T> extends WSManBaseEnumerateCommand<T> {

    private WSManClassEnum command = null;
    
    public DefaultEnumerate(WSManClassEnum command) {
        super();
        this.command = command;
    }

    @Override
    public T parse(String xml) throws WSManException {
        Document tempDoc = WSManUtils.toDocument(xml);
        return (T) WSManUtils.toObjectMap(tempDoc);
    }

    @Override
    public WSManClassEnum getCommandEnum() {
        return command;
    }

}
