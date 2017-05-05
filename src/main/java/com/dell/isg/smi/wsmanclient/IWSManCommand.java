/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.w3._2003._05.soap_envelope.Body;

/**
 * The {@code IWSManCommand} interface defines metadata around the format of the WS-Management command to send, as well as a {@code parse} method to parse the returned SOAP XML
 * payload into a POJO object for easy use by the caller.
 *
 * @param <T> The response payload type.
 */
public interface IWSManCommand<T> {
    /**
     * The resource class URI for the request.
     *
     * @return The resource URI.
     */
    String getResourceURI();


    /**
     * The action URI for the request.
     *
     * @return The action URI.
     */
    String getAction();


    /**
     * The selectors for the request. The selectors are represented by a list of key/value pairs and target the request to a specific resource instance.
     *
     * @return The list of selectors.
     */
    List<Pair<String, String>> getSelectors();


    /**
     * The selectors for the request. The selectors are represented by a list of key/value pairs and target the request to a specific resource instance.
     *
     * @return The list of selectors.
     */
    String getFilterWQL();


    /**
     * The timeout for the request. Should be in the format of an xml schema Duration data type.
     *
     * @return The timeout for the request.
     */
    String getTimeout();


    /**
     * The Body for the SOAP request.
     *
     * @return The body.
     */
    Body getBody();


    /**
     * Parses the SOAP XML response into a POJO of type {@code T}
     *
     * @param xml The SOAP XML response.
     * @return The POJO response.
     * @throws WSManException If an invalid XML response was received.
     */
    T parse(String xml) throws WSManException;
}
