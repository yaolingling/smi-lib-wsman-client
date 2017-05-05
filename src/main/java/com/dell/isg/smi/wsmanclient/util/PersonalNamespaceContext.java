/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.util;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonalNamespaceContext implements NamespaceContext {
    private String strNameSpace;
    private static final Logger logger = LoggerFactory.getLogger(PersonalNamespaceContext.class);


    public PersonalNamespaceContext(String strNS) {
        super();
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: PersonalNamespaceContext(String strNS - %s)", strNS));
        }
        this.strNameSpace = strNS;
        logger.trace("Exiting constructor: PersonalNamespaceContext()");
    }


    public String getNamespaceURI(String prefix) {
        if (prefix == null)
            throw new NullPointerException("Null prefix");
        else if ("wsman".equals(prefix))
            return "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd";
        else if ("wsa".equals(prefix))
            return "http://schemas.xmlsoap.org/ws/2004/08/addressing";
        else if ("pre".equals(prefix))
            return this.strNameSpace;
        else if ("xml".equals(prefix))
            return XMLConstants.XML_NS_URI;
        return XMLConstants.NULL_NS_URI;
    }


    // This method isn't necessary for XPath processing.
    public String getPrefix(String uri) {
        throw new UnsupportedOperationException();
    }


    // This method isn't necessary for XPath processing either.
    public Iterator getPrefixes(String uri) {
        throw new UnsupportedOperationException();
    }


    public String getStrNameSpace() {
        return strNameSpace;
    }


    public void setStrNameSpace(String strNameSpace) {
        this.strNameSpace = strNameSpace;
    }
}
