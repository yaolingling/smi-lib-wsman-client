/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.tuple.Pair;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableEmpty;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributablePositiveInteger;
import org.dmtf.schemas.wbem.wsman._1.wsman.EnumerationModeType;
//import org.dmtf.schemas.wbem.wsman._1.wsman.FilterMixedDataType;
import org.w3._2003._05.soap_envelope.Body;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Enumerate;

import com.dell.isg.smi.wsmanclient.util.WSManUtils;

/**
 * The {@code WSManBaseEnumerateCommand} class provides an abstract base class for implementers of WS-Management Enumerate commands to extend. It provides default implementations
 * for most of the {@link IWSManClient} interface. Subclasses must override at least {@code getCommandEnum} and {@code parse}.
 * 
 * Example usage is shown in the following code:
 * 
 * public class EnumerateSystemViewsCmd extends WSManBaseEnumerateCommand List SystemView {
 *     public WSManConstants.WSManClassEnum getCommandEnum() {
 *         return WSManConstants.WSManClassEnum.DCIM_SystemView;
 *     }
 *
 * 
 *     public List SystemView  parse(String xml) throws WSManException {
 *         return WSManUtils.parseEnumerationResponse(xml, SystemView.class, getCommandEnum());
 *     }
 * }
 *
 * @param <T> The type of POJO that {@code parse} returns.
 */
public abstract class WSManBaseEnumerateCommand<T> implements IWSManCommand<T> {
    public static final String ENUMERATION_NAMESPACE = "http://schemas.xmlsoap.org/ws/2004/09/enumeration";
    public static final String WSMAN_NAMESPACE = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd";
    public static final String WSMAN_WQL_NAMESPACE = "http://schemas.microsoft.com/wbem/wsman/1/WQL";


    /* (non-Javadoc)
     * @see com.dell.isg.smi.wsmanclient.IWSManCommand#getAction()
     */
    @Override
    public String getAction() {
        return ENUMERATION_NAMESPACE + "/Enumerate";
    }


    public abstract WSManConstants.WSManClassEnum getCommandEnum();


    /* (non-Javadoc)
     * @see com.dell.isg.smi.wsmanclient.IWSManCommand#getResourceURI()
     */
    @Override
    public String getResourceURI() {
        return WSManUtils.buildResourceURI(getCommandEnum());
    }


    /* (non-Javadoc)
     * @see com.dell.isg.smi.wsmanclient.IWSManCommand#getSelectors()
     */
    @Override
    public List<Pair<String, String>> getSelectors() {
        return null;
    }


    /* (non-Javadoc)
     * @see com.dell.isg.smi.wsmanclient.IWSManCommand#getFilterWQL()
     */
    @Override
    public String getFilterWQL() {
        return null;
    }


    /* (non-Javadoc)
     * @see com.dell.isg.smi.wsmanclient.IWSManCommand#getTimeout()
     */
    @Override
    public String getTimeout() {
        return "PT60.001S";
    }


    /**
     * Returns the enumeration mode for the request. The default mode, {@code EnumerateObject} causes only the resource instance items XML to be returned. Subclasses may override
     * this method and return {@code EnumerateObjectAndEPR} which will cause the returned XML items to include both the resource instance XML and the corresponding
     * EndpointReference that may be used to address the resource instance in later {@code Get} or {@code Put} commands.
     *
     * @return The enumeration request mode
     */
    protected WSManConstants.WSManEnumerationMode getMode() {
        return WSManConstants.WSManEnumerationMode.EnumerateObject;
    }


    /**
     * The maximum number of resource instances that should be returned for this enumeration request. Subclasses should override this method in order to change the default,
     * {@code 256L}.
     *
     * @return The maximum number of resource instances to return.
     */
    public long getMaxElements() {
        return 2048L;
    }


    /* (non-Javadoc)
     * @see com.dell.isg.smi.wsmanclient.IWSManCommand#getBody()
     */
    @Override
    public Body getBody() {
        org.w3._2003._05.soap_envelope.ObjectFactory env = new org.w3._2003._05.soap_envelope.ObjectFactory();
        org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory wsman = new org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory();
        org.xmlsoap.schemas.ws._2004._09.enumeration.ObjectFactory wsen = new org.xmlsoap.schemas.ws._2004._09.enumeration.ObjectFactory();

        Body body = env.createBody();

        Enumerate enumerate = wsen.createEnumerate();
        body.getAny().add(enumerate);

        switch (getMode()) {
        case EnumerateEPR:
            enumerate.getAny().add(wsman.createEnumerationMode(EnumerationModeType.ENUMERATE_EPR));
            break;
        case EnumerateObjectAndEPR:
            enumerate.getAny().add(wsman.createEnumerationMode(EnumerationModeType.ENUMERATE_OBJECT_AND_EPR));
            break;
        case EnumerateObject:
            break; // default, do nothing
        }

        AttributableEmpty empty = wsman.createAttributableEmpty();
        JAXBElement<AttributableEmpty> optimizeEnumeration = wsman.createOptimizeEnumeration(empty);
        enumerate.getAny().add(optimizeEnumeration);

        AttributablePositiveInteger max = wsman.createAttributablePositiveInteger();
        max.setValue(BigInteger.valueOf(getMaxElements()));
        JAXBElement<AttributablePositiveInteger> maxElements = wsman.createMaxElements(max);
        enumerate.getAny().add(maxElements);

        /*
         * if (getFilterWQL() != null) { FilterMixedDataType filterDataType = wsman.createFilterMixedDataType(); filterDataType.setDialect(WSMAN_WQL_NAMESPACE);
         * filterDataType.getContent().add(getFilterWQL()); JAXBElement<FilterMixedDataType> filter = wsman.createFilter(filterDataType); enumerate.getAny().add(filter); }
         */

        return body;
    }
}
