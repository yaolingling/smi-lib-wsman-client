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


    public NamespaceFilteringXMLStreamReader(XMLStreamReader delegate, String fromNamespaceURI, String toNamespaceURI) {
        this.delegate = delegate;

        // The intern() calls below are because some StAX implementations use an InterningXMLVisitor which
        // keys namespaces off of their interned values. These will fail to match the namespaces
        // we return even though they are equal() because they are not == !
        this.fromNamespaceURI = fromNamespaceURI.intern();
        this.toNamespaceURI = toNamespaceURI.intern();
    }


    protected String incomingNamespace(String fromNS) {
        if (fromNS == null || !fromNS.equals(toNamespaceURI))
            return fromNS;
        else
            return fromNamespaceURI;
    }


    protected String outgoingNamespace(String fromNS) {
        if (fromNS != null && fromNS.equals(fromNamespaceURI))
            return toNamespaceURI;
        else
            return fromNS;
    }


    @Override
    public Object getProperty(String name) throws IllegalArgumentException {
        return delegate.getProperty(name);
    }


    @Override
    public int next() throws XMLStreamException {
        return delegate.next();
    }


    @Override
    public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
        delegate.require(type, incomingNamespace(namespaceURI), localName);
    }


    @Override
    public String getElementText() throws XMLStreamException {
        return delegate.getElementText();
    }


    @Override
    public int nextTag() throws XMLStreamException {
        return delegate.nextTag();
    }


    @Override
    public boolean hasNext() throws XMLStreamException {
        return delegate.hasNext();
    }


    @Override
    public void close() throws XMLStreamException {
        delegate.close();
    }


    @Override
    public String getNamespaceURI(String prefix) {
        return outgoingNamespace(delegate.getNamespaceURI(prefix));
    }


    @Override
    public boolean isStartElement() {
        return delegate.isStartElement();
    }


    @Override
    public boolean isEndElement() {
        return delegate.isEndElement();
    }


    @Override
    public boolean isCharacters() {
        return delegate.isCharacters();
    }


    @Override
    public boolean isWhiteSpace() {
        return delegate.isWhiteSpace();
    }


    @Override
    public String getAttributeValue(String namespaceURI, String localName) {
        return delegate.getAttributeValue(incomingNamespace(namespaceURI), localName);
    }


    @Override
    public int getAttributeCount() {
        return delegate.getAttributeCount();
    }


    @Override
    public QName getAttributeName(int index) {
        QName ret = delegate.getAttributeName(index);
        String fromNS = ret.getNamespaceURI();
        if (fromNS != null && fromNS.equals(fromNamespaceURI))
            ret = new QName(toNamespaceURI, ret.getLocalPart(), ret.getPrefix());
        return ret;
    }


    @Override
    public String getAttributeNamespace(int index) {
        return outgoingNamespace(delegate.getAttributeNamespace(index));
    }


    @Override
    public String getAttributeLocalName(int index) {
        return delegate.getAttributeLocalName(index);
    }


    @Override
    public String getAttributePrefix(int index) {
        return delegate.getAttributePrefix(index);
    }


    @Override
    public String getAttributeType(int index) {
        return delegate.getAttributeType(index);
    }


    @Override
    public String getAttributeValue(int index) {
        return delegate.getAttributeValue(index);
    }


    @Override
    public boolean isAttributeSpecified(int index) {
        return delegate.isAttributeSpecified(index);
    }


    @Override
    public int getNamespaceCount() {
        return delegate.getNamespaceCount();
    }


    @Override
    public String getNamespacePrefix(int index) {
        return delegate.getNamespacePrefix(index);
    }


    @Override
    public String getNamespaceURI(int index) {
        return outgoingNamespace(delegate.getNamespaceURI(index));
    }

    protected class NamespaceFilteringNamespaceContext implements NamespaceContext {
        private final NamespaceContext delegate;


        public NamespaceFilteringNamespaceContext(NamespaceContext delegate) {
            this.delegate = delegate;
        }


        @Override
        public String getNamespaceURI(String prefix) {
            return outgoingNamespace(delegate.getNamespaceURI(prefix));
        }


        @Override
        public String getPrefix(String namespaceURI) {
            return delegate.getPrefix(namespaceURI);
        }


        @Override
        public Iterator getPrefixes(String namespaceURI) {
            return delegate.getPrefixes(namespaceURI);
        }
    }


    @Override
    public NamespaceContext getNamespaceContext() {
        return new NamespaceFilteringNamespaceContext(delegate.getNamespaceContext());
    }


    @Override
    public int getEventType() {
        return delegate.getEventType();
    }


    @Override
    public String getText() {
        return delegate.getText();
    }


    @Override
    public char[] getTextCharacters() {
        return delegate.getTextCharacters();
    }


    @Override
    public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
        return delegate.getTextCharacters(sourceStart, target, targetStart, length);
    }


    @Override
    public int getTextStart() {
        return delegate.getTextStart();
    }


    @Override
    public int getTextLength() {
        return delegate.getTextLength();
    }


    @Override
    public String getEncoding() {
        return delegate.getEncoding();
    }


    @Override
    public boolean hasText() {
        return delegate.hasText();
    }


    @Override
    public Location getLocation() {
        return delegate.getLocation();
    }


    @Override
    public QName getName() {
        QName ret = delegate.getName();
        String fromNS = ret.getNamespaceURI();
        if (fromNS != null && fromNS.equals(fromNamespaceURI))
            ret = new QName(toNamespaceURI, ret.getLocalPart(), ret.getPrefix());
        return ret;
    }


    @Override
    public String getLocalName() {
        return delegate.getLocalName();
    }


    @Override
    public boolean hasName() {
        return delegate.hasName();
    }


    @Override
    public String getNamespaceURI() {
        return outgoingNamespace(delegate.getNamespaceURI());
    }


    @Override
    public String getPrefix() {
        return delegate.getPrefix();
    }


    @Override
    public String getVersion() {
        return delegate.getVersion();
    }


    @Override
    public boolean isStandalone() {
        return delegate.isStandalone();
    }


    @Override
    public boolean standaloneSet() {
        return delegate.standaloneSet();
    }


    @Override
    public String getCharacterEncodingScheme() {
        return delegate.getCharacterEncodingScheme();
    }


    @Override
    public String getPITarget() {
        return delegate.getPITarget();
    }


    @Override
    public String getPIData() {
        return delegate.getPIData();
    }
}
