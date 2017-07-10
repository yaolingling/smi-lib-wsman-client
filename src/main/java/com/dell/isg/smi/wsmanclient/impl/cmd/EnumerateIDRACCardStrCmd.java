/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.impl.cmd;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsmanclient.WSCommandRNDConstant;
import com.dell.isg.smi.wsmanclient.WSManBaseEnumerateCommand;
import com.dell.isg.smi.wsmanclient.WSManConstants;
import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsmanclient.impl.model.IDRACCardStringView;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;

public class EnumerateIDRACCardStrCmd extends WSManBaseEnumerateCommand<List<IDRACCardStringView>> {
    /**
     * Returns an array of IDRACCardStringView elements from the views passed in that match
     * the provided fQDD, groupID and attributeName.
     *
     * @param fQDD          The match value for view.getFQDD()
     * @param groupID       The match value for view.getGroupID()
     * @param attributeName The match value for view.getAttributeName()
     * @param views         The list of IDRACCardStringView to search
     * @return An array of matching IDRACCardStringView elements
     * @throws WSManException If one of the IDRACCardStringView elements contains an invalid
     *                        GroupID or the found instance indexes do not form a contiguous array.
     */
    public static IDRACCardStringView[] getInstances(String fQDD, String groupID, String attributeName,
                                                     List<IDRACCardStringView> views)
            throws WSManException {
        // groupId is of form "SNMPAlert.1"
        String baseGroupID = StringUtils.substringBefore(groupID, ".") + ".";
        List<IDRACCardStringView> matches = new ArrayList<IDRACCardStringView>();
        for (IDRACCardStringView view : views) {
            String viewGroupID = view.getGroupID();
            if (viewGroupID != null
                    && viewGroupID.startsWith(baseGroupID)
                    && fQDD.equals(view.getfQDD())
                    && attributeName.equals(view.getAttributeName())) {
                String indexString = viewGroupID.substring(baseGroupID.length());
                try {
                    int index = Integer.valueOf(indexString) - 1;
                    if (index < 0)
                        throw new WSManException("Invalid group id: " + viewGroupID);
                    else {
                        // expand the list with null values until we can have space to set the index
                        int start = matches.size();
                        for (int i = start; i < index + 1; ++i) {
                            matches.add(null);
                        }
                        matches.set(index, view);
                    }
                } catch (NumberFormatException e) {
                    throw new WSManException("Invalid group id: " + viewGroupID);
                }
            }
        }

        // Make sure there are no gaps in our return list
        IDRACCardStringView[] ret = new IDRACCardStringView[matches.size()];
        for (
                int i = 0;
                i < matches.size(); ++i)

        {
            IDRACCardStringView view = matches.get(i);
            ret[i] = view;
            if (view == null)
                throw new WSManException("Missing " + fQDD + "#" + baseGroupID + "." + i + "#"
                        + attributeName + " instance");
        }

        return ret;
    }

    @Override
    public WSManConstants.WSManClassEnum getCommandEnum() {
        return WSManConstants.WSManClassEnum.DCIM_iDRACCardString;
    }

    @Override
    public List<IDRACCardStringView> parse(String xml) throws WSManException {
        String namespace = WSCommandRNDConstant.osdsvcdellbaseuri + getCommandEnum();
        List<IDRACCardStringView> iDracCardViewList = new ArrayList<IDRACCardStringView>();

        Document doc = WSManUtils.toDocument(xml);
        Element element = doc.getDocumentElement();
        NodeList response = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        if (response != null) {
            response = response.item(0).getChildNodes();

            for (int i = 0; i < response.getLength(); i++) {
                Node itemNode = response.item(i);
                if (itemNode.getNodeType() == Element.ELEMENT_NODE) {
                    iDracCardViewList.add(buildIDRACCardStringView(namespace, itemNode));
                }
            }
        }
        return iDracCardViewList;
    }

    static String getValue(Node childNode) {
        if (childNode.hasChildNodes()) {
            NodeList nodeList1 = childNode.getChildNodes();
            for (int k = 0; k < nodeList1.getLength(); k++) {
                Node finalNode = nodeList1.item(k);
                if (Element.TEXT_NODE == finalNode.getNodeType()) {
                    return finalNode.getNodeValue();
                }
            }
        }
        return null;
    }

    static IDRACCardStringView buildIDRACCardStringView(String ns, Node node) {
        IDRACCardStringView ret = new IDRACCardStringView();
        NodeList childList = node.getChildNodes();
        for (int i = 0; i < childList.getLength(); i++) {
            Node childNode = childList.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE && ns.equals(childNode.getNamespaceURI())) {
                String nodeName = childNode.getLocalName();
                if ("AttributeDisplayName".equals(nodeName))
                    ret.setAttributeDisplayName(getValue(childNode));
                else if ("AttributeName".equals(nodeName))
                    ret.setAttributeName(getValue(childNode));
                else if ("CurrentValue".equals(nodeName))
                    ret.setCurrentValue(getValue(childNode));
                else if ("DefaultValue".equals(nodeName))
                    ret.setDefaultValue(getValue(childNode));
                else if ("Dependency".equals(nodeName))
                    ret.setDependency(getValue(childNode));
                else if ("DisplayOrder".equals(nodeName))
                    ret.setDisplayOrder(getValue(childNode));
                else if ("FQDD".equals(nodeName))
                    ret.setfQDD(getValue(childNode));
                else if ("GroupDisplayName".equals(nodeName))
                    ret.setGroupDisplayName(getValue(childNode));
                else if ("GroupID".equals(nodeName))
                    ret.setGroupID(getValue(childNode));
                else if ("InstanceID".equals(nodeName))
                    ret.setInstanceID(getValue(childNode));
                else if ("IsReadOnly".equals(nodeName))
                    ret.setIsReadOnly(getValue(childNode));
                else if ("MaxLength".equals(nodeName))
                    ret.setMaxLength(getValue(childNode));
                else if ("MinLength".equals(nodeName))
                    ret.setMinLength(getValue(childNode));
                else if ("PendingValue".equals(nodeName))
                    ret.setPendingValue(getValue(childNode));
            }
        }

        return ret;
    }
}
