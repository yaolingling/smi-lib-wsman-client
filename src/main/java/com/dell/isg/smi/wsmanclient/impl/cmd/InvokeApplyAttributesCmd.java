/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.impl.cmd;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsmanclient.WSCommandRNDConstant;
import com.dell.isg.smi.wsmanclient.WSManBaseInvokeCommand;
import com.dell.isg.smi.wsmanclient.WSManConstants.WSManInvokableEnum;
import com.dell.isg.smi.wsmanclient.WSManConstants.WSManMethodParamEnum;
import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsmanclient.impl.model.IDRACCardStringView;
import com.dell.isg.smi.wsmanclient.impl.model.KeyValuePair;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;

/**
 * {@code InvokeApplyAttributesCmd} implements the DCIM_iDRACCardService ApplyAttributes Invoke command. See section 8.5 of
 * "Dell iDRAC Card Profile 1.3" http://en.community.dell.com/techcenter/extras/m/white_papers/20263520.aspx for further details.
 */
public class InvokeApplyAttributesCmd extends WSManBaseInvokeCommand<List<KeyValuePair>> {
    private static final Logger logger = LoggerFactory.getLogger(InvokeApplyAttributesCmd.class);
    private static final String ERROR_CODE = "2";
    private static final String JOB_CREATED_CODE = "4096";

    public static class Attribute {
        private final String groupId;
        private final String name;
        private final String value;

        public Attribute(String groupId, String name, String value) {
            this.groupId = groupId;
            this.name = name;
            this.value = value;
        }

        public Attribute(IDRACCardStringView view) {
            this.groupId = view.getGroupID();
            this.name = view.getAttributeName();
            this.value = view.getCurrentValue();
        }
    }

    private final String target;
    private final List<Attribute> attributes;

    /**
     * Constructs a new InvokeApplyAttributesCmd
     *
     * @param target     The FQDD of the resource instance to modify
     * @param attributes List of attributes to modify.
     */
    public InvokeApplyAttributesCmd(String target, List<Attribute> attributes) {
        super(WSManInvokableEnum.DCIM_iDRACCardService, WSCommandRNDConstant.APPLY_ATTRIBUTES);
        this.target = target;
        this.attributes = attributes;
    }

    @Override
    public List<Pair<String, String>> getUserParams() {
        List<Pair<String, String>> ret = new ArrayList<Pair<String, String>>();
        ret.add(Pair.of(WSManMethodParamEnum.TARGET.toString(), target));
        for (Attribute attr : attributes)
            ret.add(Pair.of(WSManMethodParamEnum.ATTRIBUTE_NAME.toString(), attr.groupId + "#" + attr.name));
        for (Attribute attr : attributes)
            ret.add(Pair.of(WSManMethodParamEnum.ATTRIBUTE_VALUE.toString(), attr.value));
        return ret;
    }

    /**
     * Parses the ApplyAttributes command response.
     *
     * @param xml The SOAP XML response.
     * @return A list of {@link KeyValuePair}. On success should contain the keys {@code returnStatus} and {@code jobId} with their corresponding
     * values.
     * @throws WSManException If the response contains a status of {@code ERROR_CODE}, or a status other than {@code JOB_CREATED_CODE}.
     */
    @Override
    public List<KeyValuePair> parse(String xml) throws WSManException {
        Document response = WSManUtils.toDocument(xml);
        Element element = response.getDocumentElement();
        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.SOAP_NAMESPACE, WSCommandRNDConstant.SOAP_BODY_TAG);
        if (nodeList == null || nodeList.getLength() != 1)
            throw new WSManException("No SOAP body in response");

        Node soapBody = nodeList.item(0);
        String status = this.parseResponseByXPath(soapBody, "ApplyAttributes", "ReturnValue");
        if (ERROR_CODE.equals(status)) {
            // TODO: provide failure details
            throw new WSManException("Invoke ApplyAttributes failed");
        } else if (!JOB_CREATED_CODE.equals(status)) {
            // TODO: provide failure details
            throw new WSManException("Invoke ApplyAttributes failed (" + status + ")");
        }

        List<KeyValuePair> keyValuePair = new ArrayList<KeyValuePair>();
        KeyValuePair keyVal1 = new KeyValuePair();
        keyVal1.setKey("returnStatus");
        keyVal1.setValue(status);
        keyValuePair.add(keyVal1);

        if (status.equals(WSCommandRNDConstant.SUCCESSFULL_CONFIG_JOB_RETURN) || status.equals(WSCommandRNDConstant.COMPLETED_WITH_NO_ERROR)) {
            String job_id = this.parseResponseByXPath(soapBody, "ApplyAttributes", "InstanceID");
            KeyValuePair keyVal2 = new KeyValuePair();
            keyVal2.setKey("jobId");
            keyVal2.setValue(job_id);
            keyValuePair.add(keyVal2);
        }

        return keyValuePair;
    }

    protected String parseResponseByXPath(Node soapBody, String jobName, String elementName) throws WSManException {
        String itemResponse = "";
        XPath xpath = XPathFactory.newInstance().newXPath();
        if (elementName.equalsIgnoreCase("InstanceID")) {
            itemResponse = this.getJobID(soapBody, xpath, jobName, elementName);
        } else if (elementName.equalsIgnoreCase("ReturnValue")) {
            itemResponse = this.getReturnValue(soapBody, xpath, jobName, elementName);
        }

        return itemResponse;
    }

    private String getReturnValue(Node soapBody, XPath xpath, String jobName, String elementName) throws WSManException {
        Object result;
        try {
            String expression = "*[local-name()='" + jobName + "_OUTPUT']";
            result = xpath.evaluate(expression, soapBody, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            String msg = "Invalid xpath expression " + xpath.toString();
            logger.error(msg, e);
            throw new WSManException(msg);
        }

        Node node = (Node) result;
        if (node.hasChildNodes()) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node childNode = nodeList.item(i);
                if (WSManUtils.isMatchingElement(childNode, elementName)) {
                    if (childNode.hasChildNodes()) {
                        NodeList nodeList1 = childNode.getChildNodes();
                        for (int k = 0; k < nodeList1.getLength(); k++) {
                            Node finalNode = nodeList1.item(k);
                            if (Element.TEXT_NODE == finalNode.getNodeType()) {
                                return finalNode.getNodeValue();
                            }
                        }
                        return null;
                    } else {
                        break;
                    }
                }
            }
        }
        return null;
    }

    private String getJobID(Node soapBody, XPath xpath, String jobName, String elementName) throws WSManException {
        Object result;
        try {
            String expression = "*[local-name()='" + jobName + "_OUTPUT']/*[local-name()='Job']";
            result = xpath.evaluate(expression, soapBody, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            String msg = "Invalid xpath expression " + xpath.toString();
            logger.error(msg, e);
            throw new WSManException(msg);
        }

        Node referenceNode = null;
        Node node = (Node) result;
        if (node.hasChildNodes()) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node childNode = nodeList.item(i);
                if (WSManUtils.isMatchingElement(childNode, "EndpointReference")) {
                    if (childNode.hasChildNodes()) {
                        NodeList nodeList2 = childNode.getChildNodes();
                        for (int j = 0; j < nodeList2.getLength(); j++) {
                            Node childNode2 = nodeList2.item(j);
                            if (WSManUtils.isMatchingElement(childNode2, "ReferenceParameters")) {
                                referenceNode = childNode2;
                                break;
                            }
                        }
                    } else {
                        referenceNode = null;
                        break;
                    }
                } else if (WSManUtils.isMatchingElement(childNode, "ReferenceParameters")) {
                    referenceNode = childNode;
                    break;
                }
            }

            if (referenceNode != null && referenceNode.hasChildNodes()) {
                NodeList nodeList2 = referenceNode.getChildNodes();
                for (int i = 0; i < nodeList2.getLength(); i++) {
                    Node childNode = nodeList2.item(i);
                    if (WSManUtils.isMatchingElement(childNode, "SelectorSet")) {
                        if (childNode.hasChildNodes()) {
                            NodeList nodeList3 = childNode.getChildNodes();
                            for (int j = 0; j < nodeList3.getLength(); j++) {
                                Node selectorNode = nodeList3.item(j);
                                if (WSManUtils.isMatchingElement(selectorNode, "Selector")) {
                                    NamedNodeMap attributes = selectorNode.getAttributes();
                                    if (attributes != null && attributes.getLength() > 0) {
                                        Node attrNode = attributes.getNamedItem("Name");
                                        if (attrNode != null && attrNode.getNodeValue() != null) {
                                            if (attrNode.getNodeValue().equalsIgnoreCase(elementName)) {
                                                if (selectorNode.hasChildNodes()) {
                                                    NodeList nodeList4 = selectorNode.getChildNodes();
                                                    for (int k = 0; k < nodeList4.getLength(); k++) {
                                                        Node finalNode = nodeList4.item(k);
                                                        if (Element.TEXT_NODE == finalNode.getNodeType()) {
                                                            return finalNode.getNodeValue();
                                                        }
                                                    }
                                                    return null;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            return null;
                        }
                    }
                }
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public String getFilterWQL() {
        return null;
    }

}
