/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient;

import java.io.IOException;

/**
 * The {@code IWSManCompoundCommand} interface is a generic marker for helper classes that provide the business logic required to execute actions that require more than one
 * WS-Management SOAP call to complete.
 *
 * @param <T> Type of POJO returned by (@code execute)
 */
public interface IWSManCompoundCommand<T> {
    /**
     * Executes the compound command and returns the response as a POJO
     *
     * @param client The {@code IWSManClient} to use to execute the WS-Management calls required.
     * @return The response as a POJO
     * @throws IOException If a network error occurred while making the WS-Management SOAP call.
     * @throws WSManException If an invalid XML response was received.
     */
    T execute(IWSManClient client) throws IOException, WSManException;
}
