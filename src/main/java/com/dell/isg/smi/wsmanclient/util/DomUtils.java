/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * The Class DomUtils.
 */
public final class DomUtils {
    private DomUtils() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DomUtils.class);

    public static final StringConverter<String> STRING_CONVERTER = new NoopConverter();
    public static final StringConverter<Integer> INTEGER_CONVERTER = new IntegerConverter();


    /**
     * Gets the local name.
     *
     * @param node the node
     * @return the local name
     */
    public static String getLocalName(Node node) {
        if (node == null || !(node instanceof Element)) {
            return null;
        }
        Element elem = (Element) node;
        String tagName = elem.getTagName();
        // tag name may be namespace qualified, e.g. something like "n1:tagName"
        int i = tagName.indexOf(':');
        return (i < 0) ? tagName : tagName.substring(i + 1);
    }


    /**
     * Helper function for processing list values on a DOM tree. Given a list adds the node's text content to it and returns that list if the node is not nil. If the node is nil
     * (which indicates that the list itself is null), returns null.
     *
     * @param node the node
     * @param list the list
     * @return null if the node is nil; otherwise adds the node's text content as an element to the list and returns it
     */
    public static List<String> updateListFromNode(Node node, List<String> list) {
        return updateListFromNode(node, list, STRING_CONVERTER);
    }


    /**
     * Helper function for processing list values on a DOM tree. Given a list, calls the converter with the the node's text content, adds the resulting value to the list and
     * returns that list if the node is not nil. If the node is nil (which indicates that the list itself is null), returns null.
     *
     * @param <T> the generic type
     * @param node the node
     * @param list the list
     * @param converter converts the node's text content prior to adding it to the list
     * @return null if the node is nil; otherwise adds the node's text content as an element to the list and returns it
     */
    public static <T> List<T> updateListFromNode(Node node, List<T> list, StringConverter<T> converter) {
        NamedNodeMap attributes = node.getAttributes();
        Node nil = attributes.getNamedItemNS("http://www.w3.org/2001/XMLSchema-instance", "nil");

        if (nil != null && "true".equals(nil.getTextContent())) {
            if (list != null && list.size() > 0) {
                LOGGER.warn("Node " + node.getNodeName() + " is nil but the corresponding list is not" + " empty and will be erased: " + list);
            }
            return null;
        } else {
            if (list == null)
                list = new ArrayList<T>();
            list.add(converter.convert(node.getTextContent()));
        }
        return list;
    }

    /**
     * The Interface StringConverter.
     *
     * @param <T> the generic type
     */
    public interface StringConverter<T> {
        
        /**
         * Convert.
         *
         * @param s the s
         * @return the t
         */
        T convert(String s);
    }

    /**
     * The Class NoopConverter.
     */
    public static class NoopConverter implements StringConverter<String> {
        
        /* (non-Javadoc)
         * @see com.dell.isg.smi.wsmanclient.util.DomUtils.StringConverter#convert(java.lang.String)
         */
        @Override
        public String convert(String s) {
            return s;
        }
    }

    /**
     * The Class IntegerConverter.
     */
    public static class IntegerConverter implements StringConverter<Integer> {
        
        /* (non-Javadoc)
         * @see com.dell.isg.smi.wsmanclient.util.DomUtils.StringConverter#convert(java.lang.String)
         */
        @Override
        public Integer convert(String s) {
            return (s == null) ? null : Integer.valueOf(s);
        }
    }
}
