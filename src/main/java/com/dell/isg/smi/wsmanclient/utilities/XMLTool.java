/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.utilities;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Utility class to aid with searching for nodes in a given DOM document. Methods can return either text values or nodes depending on the type of search requested.
 *
 */
public class XMLTool {
    /**
     * Inner class to store a Node list, exposed as an Interface
     */
    public class XMLToolNodeList implements NodeList {
        private List<Node> nodeList = new ArrayList<>();


        /* (non-Javadoc)
         * @see org.w3c.dom.NodeList#item(int)
         */
        @Override
        public Node item(int index) {
            return nodeList.get(index);
        }


        /* (non-Javadoc)
         * @see org.w3c.dom.NodeList#getLength()
         */
        @Override
        public int getLength() {
            return nodeList.size();
        }


        /**
         * Adds the.
         *
         * @param nodeToAdd the node to add
         */
        private void add(Node nodeToAdd) {
            nodeList.add(nodeToAdd);
        }

    }

    /**
     * The search scope is used for criteria in matching a node name.
     */
    public enum NameScope {
        /**
         * default, search scope is just the node name
         */
        Name,
        /**
         * set the search scope to the local name of the node
         */
        LocalName;
    }

    /**
     * Internal value to determine how to walk nodes, and what conditions will terminate the recursive node walk.
     */
    private enum WalkType {

        FindNode, FindText, PairMatch, PairGroup, ElementName, NameSpaceURI, ElementValue, Leaf;
    }

    private boolean debugFlag = false;
    private boolean stopWalk = false;
    private XMLToolNodeList nodeList = new XMLToolNodeList();
    private Node targetNode = null;
    private String targetValue = null;
    private String targetName = null;
    private Document sourceDocument = null;

    private NameScope searchScope = NameScope.Name;

    LinkedHashMap<String, String> namespaces = new LinkedHashMap<>();


    /**
     * Instantiates a new XML tool.
     *
     * @param docToLoad the doc to load
     */
    public XMLTool(Document docToLoad) {
        this.sourceDocument = docToLoad;
    }


    /**
     * Sets the search scope.
     *
     * @param newScope the new search scope
     */
    public void setSearchScope(NameScope newScope) {
        this.searchScope = newScope;
    }


    /**
     * Toggles debug mode.
     */
    public void setDebug() {
        debugFlag = !debugFlag;
    }


    /**
     * Load doc.
     *
     * @param docToLoad the doc to load
     */
    public void loadDoc(Document docToLoad) {
        this.sourceDocument = docToLoad;
    }


    /**
     * Gets the target node.
     *
     * @param name - Node name to search for, name can be local if the scope is set
     * @return First Node that matched search
     */
    public Node getTargetNode(String name) {
        targetName = name;
        stopWalk = false;
        walk(sourceDocument.getFirstChild(), WalkType.FindNode);

        return targetNode;
    }


    /**
     * Gets the target nodes.
     *
     * @param name - Node name to search for, name can be local if the scope is set
     * @return NodeList of all Nodes that matched search
     */
    public NodeList getTargetNodes(String name) {
        targetName = name;
        stopWalk = false;
        walk(sourceDocument.getFirstChild(), WalkType.PairGroup);

        return nodeList;
    }


    /**
     * Gets the target node.
     *
     * @param name - Node name to search for, name can be local if the scope is set
     * @param value - Node value to search for that is part of a found Node
     * @return the target node
     */
    public Node getTargetNode(String name, String value) {
        targetName = name;
        targetValue = value;
        stopWalk = false;
        walk(sourceDocument.getFirstChild(), WalkType.PairMatch);

        return targetNode;
    }


    /**
     * Gets the target value.
     *
     * @param value the value
     * @return the target value
     */
    public String getTargetValue(String value) {
        targetValue = null;
        stopWalk = false;
        walk(sourceDocument.getFirstChild(), WalkType.ElementValue);

        return targetValue;
    }


    /**
     * Gets the target node.
     *
     * @return Node - Last Node found
     */
    public Node getTargetNode() {
        stopWalk = false;
        return targetNode;
    }


    /**
     * Gets the value.
     *
     * @return String - Last Value found
     */
    public String getValue() {
        stopWalk = false;
        return targetValue;
    }


    /**
     * Gets the value from child.
     *
     * @param parent the parent
     * @return value - The value a child node such that <node> value </node>, common in CIM
     */
    public static String getValueFromChild(Node parent) {
        return parent.getFirstChild().getNodeValue();
    }


    /**
     * Put.
     *
     * @param prefix the prefix
     * @param uri the uri
     */
    private void put(String prefix, String uri) {
        this.namespaces.put(prefix, uri);
    }


    /**
     * Walk. Recursive walk that will set a targetNode or group of Nodes based on search criteria
     *
     * @param node the node
     * @param walkType the walk type
     */
    private void walk(Node node, WalkType walkType) {
        int type = node.getNodeType();
        String nameSpaceName = node.getNamespaceURI();
        String prefixName = node.getPrefix();
        String nodeValue = node.getNodeValue();
        String nodeName = node.getNodeName();
        String nodeLocalName = node.getLocalName();
        this.targetNode = node;

        String searchName = nodeName;

        switch (searchScope) {
        case Name:
            searchName = nodeName;
            break;
        case LocalName:
            if (nodeLocalName != null)
                searchName = nodeLocalName;
            break;
        }

        if (debugFlag) {
            System.out.println("------------------------------");
            System.out.println("name: " + nodeName);
            System.out.println("value: " + nodeValue);
            System.out.println("localName: " + nodeLocalName);
            System.out.println("Prefix Name: " + prefixName);
            System.out.println("==============================");
        }

        switch (type) {
        case Node.DOCUMENT_NODE:
            break;
        case Node.ELEMENT_NODE:
            break;

        case Node.ENTITY_REFERENCE_NODE:
            break;

        case Node.CDATA_SECTION_NODE:
            break;

        case Node.TEXT_NODE:
            if (nodeValue == null) // nil case
            {
                nodeValue = "0";
            }
            break;
        case Node.PROCESSING_INSTRUCTION_NODE:
            break;
        }// end of switch

        // based on walk type, do something
        switch (walkType) {
        case NameSpaceURI:
            put(nameSpaceName, prefixName);
            break;
        case ElementValue:
            if (nodeValue.equals(targetValue)) {
                stopWalk = true;
            }
            break;
        case FindNode:
            if (searchName.equals(targetName)) {
                stopWalk = true;
            }
            break;

        case PairGroup:
            if (searchName.equals(targetName)) {
                this.nodeList.add(targetNode);
            }
            break;

        case FindText:
            if (nodeName != null && searchName.equals(targetName)) {
                // walk the children until you find a text node
                NodeList cl = node.getChildNodes();
                for (int i = 0; i < cl.getLength(); i++) {
                    Node child = cl.item(i);
                    if (child != null && (child.getNodeType() == Node.TEXT_NODE || child.getNodeType() == Node.ELEMENT_NODE)) {
                        String childValue = child.getNodeValue();
                        if (childValue != null && !childValue.trim().isEmpty()) {
                            targetValue = childValue;
                            stopWalk = true;
                        }
                    }

                }
            }

            break;

        case PairMatch:
            if (nodeValue != null && nodeValue.equals(targetValue)) {
                // now that we found the value, make sure the
                // parent's name matches
                Node parentNode = node.getParentNode();
                String parentNodeName = parentNode.getNodeName();
                if (parentNodeName == null)
                    parentNodeName = "";
                String parentLocalName = parentNode.getLocalName();
                if (parentLocalName == null)
                    parentLocalName = "";

                if (parentNodeName.equals(targetName) || parentLocalName.equals(targetName)) {
                    targetNode = parentNode;
                    stopWalk = true;
                }
            } else if (nodeName.equals(targetName) && (nodeValue != null) && nodeValue.equals(targetValue)) {
                stopWalk = true;
            }
            break;
        } // end of switch
          // since we are recursive, if we found what we want, stop looking
          // for it
          // recurse
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (!stopWalk)
                walk(child, walkType);
        }
    }// end of walk`


    /**
     * Prints the doc to string.
     *
     * @param document the document
     * @return the string
     * @throws ParserConfigurationException the parser configuration exception
     * @throws TransformerException the transformer exception
     * @throws SAXException the SAX exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String printDocToString(Document document) throws ParserConfigurationException, TransformerException, SAXException, IOException {

        DOMSource domSource = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        return writer.toString();
    }


    /**
     * Prints the doc to formatted string.
     *
     * @param doc the doc
     * @return the string
     */
    public static String printDocToFormattedString(Document doc) {
        String outString = "";
        /*
         * Writer writer = new StringWriter(); OutputFormat format = new OutputFormat(doc); format.setLineWidth(65); format.setIndenting(true); format.setIndent(2); XMLSerializer
         * serializer = new XMLSerializer(writer, format); try { serializer.serialize(doc); outString = writer.toString(); } catch (IOException e) { // TODO Auto-generated catch
         * block e.printStackTrace(); }
         */

        return outString;
    }


    /**
     * Prints the to sys out.
     *
     * @param doc the doc
     */
    public static void printToSysOut(Document doc) {
        Writer writer = new OutputStreamWriter(System.out);
        /*
         * OutputFormat format = new OutputFormat(doc); format.setLineWidth(65); format.setIndenting(true); format.setIndent(2); XMLSerializer serializer = new
         * XMLSerializer(writer, format); try { serializer.serialize(doc); } catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
         */
    }


    /**
     * Gets the text value from node.
     *
     * @param parent the parent
     * @param valueToGet the value to get
     * @return the text value from node
     */
    public String getTextValueFromNode(Node parent, String valueToGet) {
        targetName = valueToGet;
        stopWalk = false;
        targetValue = "";
        walk(parent, WalkType.FindText);

        return targetValue;

    }


    /**
     * Clone document.
     *
     * @param docToClone the doc to clone
     * @return the document
     */
    public static Document cloneDocument(Document docToClone) {
        TransformerFactory tfactory = TransformerFactory.newInstance();
        DOMSource source = new DOMSource(docToClone);
        Transformer tx = null;
        Document clonedDoc = null;
        try {
            tx = tfactory.newTransformer();
            source = new DOMSource(docToClone);
        } catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DOMResult result = new DOMResult();
        if (tx != null) {
            try {
                tx.transform(source, result);
                if (result != null) {
                    clonedDoc = (Document) result.getNode();
                }
            } catch (TransformerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return clonedDoc;
    }

    // public static String convertAddressingToString( Addressing addressingToConvert ) throws ParserConfigurationException, SOAPException, TransformerException, SAXException,
    // IOException
    // {
    // Document tempDoc = addressingToConvert.getBody().extractContentAsDocument();
    // return XMLTool.printDocToString(tempDoc);
    // }
} // end of class
