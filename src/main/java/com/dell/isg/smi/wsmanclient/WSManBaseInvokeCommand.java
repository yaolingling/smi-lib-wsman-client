/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.w3._2003._05.soap_envelope.Body;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dell.isg.smi.wsmanclient.WSManConstants.WSManMethodParamEnum;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;

/**
 * The {@code WSManBaseInvokeCommand} class provides an abstract base class for implementers of WS-Management Invoke commands to extend. It provides default implementations for
 * most of the {@link IWSManClient} interface.
 * 
 * The constructor must be supplied with the {@code WSManClassEnum} and {@code Invoke} method name. Additionally, subclasses should override {@code getUserParams} if they take
 * input parameters. The `List<Pair<String, String>>` return value will be used by the base class
 *
 * @param <T> The type of POJO that {@code parse} returns.
 */
public abstract class WSManBaseInvokeCommand<T> implements IWSManCommand<T> {
    protected final IWSManInvokableEnum cmd;
    protected final String actionName;


    /**
     * Default constructor.
     *
     * @param cmd The WSManInvokableEnum used to address the resource class.
     * @param actionName The invoke method name.
     */
    public WSManBaseInvokeCommand(IWSManInvokableEnum cmd, String actionName) {
        this.cmd = cmd;
        this.actionName = actionName;
    }


    @Override
    public String getResourceURI() {
        return WSManUtils.buildResourceURI((Enum) cmd.getCommandEnum());
    }


    @Override
    public String getAction() {
        return getResourceURI() + "/" + actionName;
    }


    /**
     * The input parameter specified as a list of key/value pairs. Subclasses must override this method. The should return null if the {@code Invoke} method does not take an input
     * parameter.
     *
     * @return The {@code Invoke} method input parameters.
     */
    public abstract List<Pair<String, String>> getUserParams();


    @Override
    public List<Pair<String, String>> getSelectors() {
        List<Pair<String, String>> ret = new ArrayList<Pair<String, String>>();
        ret.add(Pair.of(WSManMethodParamEnum.SYSTEM_NAME.toString(), cmd.getSystemName()));
        ret.add(Pair.of(WSManMethodParamEnum.NAME.toString(), cmd.getName()));
        ret.add(Pair.of(WSManMethodParamEnum.CREATION_CLASS_NAME.toString(), cmd.getCreationClassName()));
        ret.add(Pair.of(WSManMethodParamEnum.SYSTEM_CLASS_NAME.toString(), cmd.getSystemCreationClassName()));
        ret.add(Pair.of("__cimnamespace", "root/dcim"));
        // ElementName is listed in the DCIM docs as mandatory but get InvalidSelector error when specified
        // ret.add(Pair.of("ElementName", cmd.getElementName()));
        return ret;
    }


    @Override
    public String getTimeout() {
        return null;
    }


    @Override
    public Body getBody() {
        Document document = WSManUtils.newDocument();

        org.w3._2003._05.soap_envelope.ObjectFactory env = new org.w3._2003._05.soap_envelope.ObjectFactory();

        String ns = getResourceURI();

        Body body = env.createBody();
        Element inputElem = document.createElementNS(ns, actionName + "_INPUT");
        inputElem.setPrefix("ns1");
        body.getAny().add(inputElem);
        for (Pair<String, String> kvPair : getUserParams()) {
            Element param = document.createElementNS(ns, kvPair.getKey());
            param.appendChild(document.createTextNode(kvPair.getValue()));
            param.setPrefix("ns1");
            inputElem.appendChild(param);
        }

        return body;
    }
}
