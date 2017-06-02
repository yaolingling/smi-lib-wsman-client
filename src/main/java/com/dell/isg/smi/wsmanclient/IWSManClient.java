/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient;

import java.io.IOException;

/**
 * The {@code IWSManClient} interface is used to send and receive {@link IWSManCommand} commands. An instance can be obtained via one of the {@link WSManClientFactory} getClient
 * methods.
 * 
 * The following code demonstrates sample usage:
 * 
 * IWSManClient client = WSManClientFactory.getClient(ip, user, password);
 *try {
 *     EnumeratePSNumericSensorCmd numericSensorCmd = new EnumeratePSNumericSensorCmd();
 *     List PSNumericView numericViews = client.execute(numericSensorCmd);
 *
 *     EnumerateSystemViewObjectCmd systemViewCmd = new EnumerateSystemViewObjectCmd();
 *     SystemView systemView = client.execute(systemViewCmd);
 * } finally {
 *     client.close();
 * }
 */
public interface IWSManClient {
    /**
     * Execute the command {@code cmd} and return its payload parsed into a POJO of type {@code T}.
     *
     * @param cmd The command to execute.
     * @param <T> The type of POJO returned.
     * @return A POJO representation of the response.
     * @throws IOException If a network error occurred while making the WS-Management SOAP call.
     * @throws WSManException If an invalid XML response was received.
     */
    <T> T execute(IWSManCommand<T> cmd) throws IOException, WSManException;


    /**
     * Execute the command {@code cmd} and return its payload as an XML {@code String}.
     *
     * @param cmd The command to execute.
     * @param <T> The type of POJO the command operates on.
     * @return The WS-Management SOAP response as an XML {@code String}.
     * @throws IOException If a network error occurred while making the WS-Management SOAP call.
     * @throws WSManException If an invalid XML response was received.
     */
    <T> String executeXML(IWSManCommand<T> cmd) throws IOException, WSManException;


    /**
     * Release any resources held by {@code this} client.
     */
    void close();


    /**
     * Get the IP address {@code this} client will communicate with.
     *
     * @return The IP address.
     */
    String getIpAddress();


    /**
     * Get the username this {@code this} client will communicate with.
     *
     * @return The username.
     */
    String getUser();


    /**
     * Get the password this {@code this} client will communicate with.
     *
     * @return The password.
     */
    String getPassword();
}
