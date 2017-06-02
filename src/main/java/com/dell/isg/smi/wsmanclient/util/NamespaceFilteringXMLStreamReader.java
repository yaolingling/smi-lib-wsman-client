/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.util;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * XMLStreamReader which makes the namespace fromNamespaceURI in the delegate XMLStreamReader appear as toNamespaceURI to its callers.
 */
public class NamespaceFilteringXMLStreamReader implements XMLStreamReader {
    private final XMLStreamReader delegate;
    private final String fromNamespaceURI;
    private final String toNamespaceURI;


    /**
     * Instantiates a new namespace filtering XML stream reader.
     *
     * @param delegate the delegate
     * @param fromNamespaceURI the from namespace URI
     * @param toNamespaceURI the to namespace URI
     */
    public NamespaceFilteringXMLStreamReader(XMLStreamReader delegate, String fromNamespaceURI, String toNamespaceURI) {
        this.delegate = delegate;

        // The intern() calls below are because some StAX implementations use an InterningXMLVisitor which
        // keys namespaces off of their interned values. These will fail to match the namespaces
        // we return even though they are equal() because they are not == !
        this.fromNamespaceURI = fromNamespaceURI.intern();
        this.toNamespaceURI = toNamespaceURI.intern();
    }


    /**
     * Incoming namespace.
     *
     * @param fromNS the from NS
     * @return the string
     */
    protected String incomingNamespace(String fromNS) {
        if (fromNS == null || !fromNS.equals(toNamespaceURI))
            return fromNS;
        else
            return fromNamespaceURI;
    }


    /**
     * Outgoing namespace.
     *
     * @param fromNS the from NS
     * @return the string
     */
    protected String outgoingNamespace(String fromNS) {
        if (fromNS != null && fromNS.equals(fromNamespaceURI))
            return toNamespaceURI;
        else
            return fromNS;
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getProperty(java.lang.String)
     */
    @Override
    public Object getProperty(String name) throws IllegalArgumentException {
        return delegate.getProperty(name);
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#next()
     */
    @Override
    public int next() throws XMLStreamException {
        return delegate.next();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#require(int, java.lang.String, java.lang.String)
     */
    @Override
    public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
        delegate.require(type, incomingNamespace(namespaceURI), localName);
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getElementText()
     */
    @Override
    public String getElementText() throws XMLStreamException {
        return delegate.getElementText();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#nextTag()
     */
    @Override
    public int nextTag() throws XMLStreamException {
        return delegate.nextTag();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#hasNext()
     */
    @Override
    public boolean hasNext() throws XMLStreamException {
        return delegate.hasNext();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#close()
     */
    @Override
    public void close() throws XMLStreamException {
        delegate.close();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getNamespaceURI(java.lang.String)
     */
    @Override
    public String getNamespaceURI(String prefix) {
        return outgoingNamespace(delegate.getNamespaceURI(prefix));
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#isStartElement()
     */
    @Override
    public boolean isStartElement() {
        return delegate.isStartElement();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#isEndElement()
     */
    @Override
    public boolean isEndElement() {
        return delegate.isEndElement();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#isCharacters()
     */
    @Override
    public boolean isCharacters() {
        return delegate.isCharacters();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#isWhiteSpace()
     */
    @Override
    public boolean isWhiteSpace() {
        return delegate.isWhiteSpace();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getAttributeValue(java.lang.String, java.lang.String)
     */
    @Override
    public String getAttributeValue(String namespaceURI, String localName) {
        return delegate.getAttributeValue(incomingNamespace(namespaceURI), localName);
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getAttributeCount()
     */
    @Override
    public int getAttributeCount() {
        return delegate.getAttributeCount();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getAttributeName(int)
     */
    @Override
    public QName getAttributeName(int index) {
        QName ret = delegate.getAttributeName(index);
        String fromNS = ret.getNamespaceURI();
        if (fromNS != null && fromNS.equals(fromNamespaceURI))
            ret = new QName(toNamespaceURI, ret.getLocalPart(), ret.getPrefix());
        return ret;
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getAttributeNamespace(int)
     */
    @Override
    public String getAttributeNamespace(int index) {
        return outgoingNamespace(delegate.getAttributeNamespace(index));
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getAttributeLocalName(int)
     */
    @Override
    public String getAttributeLocalName(int index) {
        return delegate.getAttributeLocalName(index);
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getAttributePrefix(int)
     */
    @Override
    public String getAttributePrefix(int index) {
        return delegate.getAttributePrefix(index);
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getAttributeType(int)
     */
    @Override
    public String getAttributeType(int index) {
        return delegate.getAttributeType(index);
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getAttributeValue(int)
     */
    @Override
    public String getAttributeValue(int index) {
        return delegate.getAttributeValue(index);
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#isAttributeSpecified(int)
     */
    @Override
    public boolean isAttributeSpecified(int index) {
        return delegate.isAttributeSpecified(index);
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getNamespaceCount()
     */
    @Override
    public int getNamespaceCount() {
        return delegate.getNamespaceCount();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getNamespacePrefix(int)
     */
    @Override
    public String getNamespacePrefix(int index) {
        return delegate.getNamespacePrefix(index);
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getNamespaceURI(int)
     */
    @Override
    public String getNamespaceURI(int index) {
        return outgoingNamespace(delegate.getNamespaceURI(index));
    }

    /**
     * The Class NamespaceFilteringNamespaceContext.
     */
    protected class NamespaceFilteringNamespaceContext implements NamespaceContext {
        private final NamespaceContext delegate;


        /**
         * Instantiates a new namespace filtering namespace context.
         *
         * @param delegate the delegate
         */
        public NamespaceFilteringNamespaceContext(NamespaceContext delegate) {
            this.delegate = delegate;
        }


        /* (non-Javadoc)
         * @see javax.xml.namespace.NamespaceContext#getNamespaceURI(java.lang.String)
         */
        @Override
        public String getNamespaceURI(String prefix) {
            return outgoingNamespace(delegate.getNamespaceURI(prefix));
        }


        /* (non-Javadoc)
         * @see javax.xml.namespace.NamespaceContext#getPrefix(java.lang.String)
         */
        @Override
        public String getPrefix(String namespaceURI) {
            return delegate.getPrefix(namespaceURI);
        }


        /* (non-Javadoc)
         * @see javax.xml.namespace.NamespaceContext#getPrefixes(java.lang.String)
         */
        @Override
        public Iterator getPrefixes(String namespaceURI) {
            return delegate.getPrefixes(namespaceURI);
        }
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getNamespaceContext()
     */
    @Override
    public NamespaceContext getNamespaceContext() {
        return new NamespaceFilteringNamespaceContext(delegate.getNamespaceContext());
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getEventType()
     */
    @Override
    public int getEventType() {
        return delegate.getEventType();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getText()
     */
    @Override
    public String getText() {
        return delegate.getText();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getTextCharacters()
     */
    @Override
    public char[] getTextCharacters() {
        return delegate.getTextCharacters();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getTextCharacters(int, char[], int, int)
     */
    @Override
    public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
        return delegate.getTextCharacters(sourceStart, target, targetStart, length);
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getTextStart()
     */
    @Override
    public int getTextStart() {
        return delegate.getTextStart();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getTextLength()
     */
    @Override
    public int getTextLength() {
        return delegate.getTextLength();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getEncoding()
     */
    @Override
    public String getEncoding() {
        return delegate.getEncoding();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#hasText()
     */
    @Override
    public boolean hasText() {
        return delegate.hasText();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getLocation()
     */
    @Override
    public Location getLocation() {
        return delegate.getLocation();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getName()
     */
    @Override
    public QName getName() {
        QName ret = delegate.getName();
        String fromNS = ret.getNamespaceURI();
        if (fromNS != null && fromNS.equals(fromNamespaceURI))
            ret = new QName(toNamespaceURI, ret.getLocalPart(), ret.getPrefix());
        return ret;
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getLocalName()
     */
    @Override
    public String getLocalName() {
        return delegate.getLocalName();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#hasName()
     */
    @Override
    public boolean hasName() {
        return delegate.hasName();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getNamespaceURI()
     */
    @Override
    public String getNamespaceURI() {
        return outgoingNamespace(delegate.getNamespaceURI());
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getPrefix()
     */
    @Override
    public String getPrefix() {
        return delegate.getPrefix();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getVersion()
     */
    @Override
    public String getVersion() {
        return delegate.getVersion();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#isStandalone()
     */
    @Override
    public boolean isStandalone() {
        return delegate.isStandalone();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#standaloneSet()
     */
    @Override
    public boolean standaloneSet() {
        return delegate.standaloneSet();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getCharacterEncodingScheme()
     */
    @Override
    public String getCharacterEncodingScheme() {
        return delegate.getCharacterEncodingScheme();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getPITarget()
     */
    @Override
    public String getPITarget() {
        return delegate.getPITarget();
    }


    /* (non-Javadoc)
     * @see javax.xml.stream.XMLStreamReader#getPIData()
     */
    @Override
    public String getPIData() {
        return delegate.getPIData();
    }
}
