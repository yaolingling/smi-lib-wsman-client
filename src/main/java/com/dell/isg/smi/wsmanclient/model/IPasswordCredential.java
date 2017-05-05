/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.model;

public interface IPasswordCredential extends ICredential {

    /**
     * Gets the username
     *
     * @return String - the username
     */
    public String getUsername();


    /**
     * Sets the username
     *
     * @param value - the username
     */
    public void setUsername(String username);


    /**
     * Gets the password
     *
     * @return String - the password
     */
    public String getPassword();


    /**
     * Sets the password
     *
     * @param value - the password
     */
    public void setPassword(String password);


    /**
     * Gets the Domain
     *
     * @return String - the domain
     */
    public String getDomain();


    /**
     * Sets the domain
     *
     * @param value - the value
     */
    public void setDomain(String domain);
}
