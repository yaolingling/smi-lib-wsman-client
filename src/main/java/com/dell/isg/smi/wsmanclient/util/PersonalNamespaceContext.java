/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.util;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PersonalNamespaceContext.
 */
public class PersonalNamespaceContext implements NamespaceContext {
    private String strNameSpace;
    private static final Logger logger = LoggerFactory.getLogger(PersonalNamespaceContext.class);


    /**
     * Instantiates a new personal namespace context.
     *
     * @param strNS the str NS
     */
    public PersonalNamespaceContext(String strNS) {
        super();
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: PersonalNamespaceContext(String strNS - %s)", strNS));
        }
        this.strNameSpace = strNS;
        logger.trace("Exiting constructor: PersonalNamespaceContext()");
    }


    /* (non-Javadoc)
     * @see javax.xml.namespace.NamespaceContext#getNamespaceURI(java.lang.String)
     */
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


    /* (non-Javadoc)
     * @see javax.xml.namespace.NamespaceContext#getPrefix(java.lang.String)
     */
    // This method isn't necessary for XPath processing.
    public String getPrefix(String uri) {
        throw new UnsupportedOperationException();
    }


    /* (non-Javadoc)
     * @see javax.xml.namespace.NamespaceContext#getPrefixes(java.lang.String)
     */
    // This method isn't necessary for XPath processing either.
    public Iterator getPrefixes(String uri) {
        throw new UnsupportedOperationException();
    }


    /**
     * Gets the str name space.
     *
     * @return the str name space
     */
    public String getStrNameSpace() {
        return strNameSpace;
    }


    /**
     * Sets the str name space.
     *
     * @param strNameSpace the new str name space
     */
    public void setStrNameSpace(String strNameSpace) {
        this.strNameSpace = strNameSpace;
    }
}
